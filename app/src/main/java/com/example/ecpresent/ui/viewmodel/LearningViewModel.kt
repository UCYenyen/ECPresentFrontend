package com.example.ecpresent.ui.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecpresent.data.container.ServerContainer
import com.example.ecpresent.data.dto.LoginUserRequest
import com.example.ecpresent.data.dto.RegisterUserRequest
import com.example.ecpresent.data.local.DataStoreManager
import com.example.ecpresent.ui.model.Avatar
import com.example.ecpresent.ui.model.User
import com.example.ecpresent.ui.model.Learning
import com.example.ecpresent.ui.model.LearningProgress
import com.example.ecpresent.ui.model.toLearning
import com.example.ecpresent.ui.model.toLearningProgress
import com.example.ecpresent.ui.uistates.LearningDetailsUIState
import com.example.ecpresent.ui.uistates.LearningProgressUIState
import com.example.ecpresent.ui.uistates.LearningUIState
import com.example.ecpresent.ui.uistates.LoginUIState
import com.example.ecpresent.ui.uistates.ProfileUIState
import com.example.ecpresent.ui.uistates.UploadPresentationUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LearningViewModel(application: Application) : AndroidViewModel(application) {

    private val learningRepository = ServerContainer().serverLearningRepository
    private val dataStoreManager = DataStoreManager(application)
    private val _learningUIState = MutableStateFlow<LearningUIState>(LearningUIState.Initial)
    val learningUIState: StateFlow<LearningUIState> = _learningUIState.asStateFlow()
    private val _learningProgressUIState =
        MutableStateFlow<LearningProgressUIState>(LearningProgressUIState.Initial)
    val learningProgressUIState: StateFlow<LearningProgressUIState> =
        _learningProgressUIState.asStateFlow()

    private val _learningDetailUIState =
        MutableStateFlow<LearningDetailsUIState>(LearningDetailsUIState.Initial)
    val learningDetailUIState: StateFlow<LearningDetailsUIState> =
        _learningDetailUIState.asStateFlow()

    init {
        viewModelScope.launch {
            getAllLearnings()
            getMyLearningProgresses()
        }
    }


    fun getAllLearnings() {
        viewModelScope.launch {
            _learningUIState.value = LearningUIState.Loading
            try {
                val response = learningRepository.getAllLearnings()
                if (response.isSuccessful && response.body() != null) {
                    val baseResponse = response.body()!!
                    val learningList = baseResponse.data.map { it.toLearning() }
                    _learningUIState.value = LearningUIState.Success(learningList)
                } else {
                    val errorBody = response.errorBody()?.string()
                    _learningUIState.value =
                        LearningUIState.Error("Fetch failed: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _learningUIState.value = LearningUIState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun getMyLearningProgresses() {
        viewModelScope.launch {
            _learningProgressUIState.value = LearningProgressUIState.Loading
            try {
                val token = dataStoreManager.tokenFlow.first()
                if (token.isNullOrEmpty()) return@launch

                val response = learningRepository.getMyLearningProgresses(token)

                if (response.isSuccessful && response.body() != null) {
                    val baseResponse = response.body()!!
                    val progressResponseList = baseResponse.data

                    val mappedList = progressResponseList.map { it.toLearningProgress() }

                    _learningProgressUIState.value = LearningProgressUIState.Success(mappedList)
                } else {
                    val errorBody = response.errorBody()?.string()
                    _learningProgressUIState.value =
                        LearningProgressUIState.Error("Fetch failed: ${response.code()} - $errorBody")
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _learningProgressUIState.value =
                    LearningProgressUIState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun startLearning(learningId: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                val token = dataStoreManager.tokenFlow.first()
                if (token.isNullOrEmpty()) return@launch

                val response = learningRepository.startLearning(token, learningId)

                if (response.isSuccessful && response.body() != null) {
                    getMyLearningProgresses()
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("Start Learning failed: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun markAsDoneLearningProgress(learningProgressId: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                val token = dataStoreManager.tokenFlow.first()
                if (token.isNullOrEmpty()) return@launch

                val response = learningRepository.completeLearning(token, learningProgressId)

                if (response.isSuccessful && response.body() != null) {
                    getMyLearningProgresses()
                    onSuccess()
                } else {
                    val errorBody = response.errorBody()?.string()
                    println("Complete Learning failed: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun getLearningById(id: String): Learning? {
        val learningState = _learningUIState.value

        val allLearnings = if (learningState is LearningUIState.Success) {
            learningState.data
        } else {
            return null
        }
        val learning = allLearnings.find { it.id == id }

        if (learning == null) {
            return null
        }

        val progressState = _learningProgressUIState.value

        val isAdded = if (progressState is LearningProgressUIState.Success) {
            progressState.data.any { progress -> progress.learning.id == id }
        } else {
            false
        }

        _learningDetailUIState.value = LearningDetailsUIState.Success(learning, isAdded)
        return learning
    }

    fun getLearningProgressById(id: String): LearningProgress? {
        val currentState = learningProgressUIState.value
        return if (currentState is LearningProgressUIState.Success) {
            currentState.data.find { it.id == id }
        } else null
    }

    fun getYoutubeThumbnailUrl(videoUrl: String): String {
        val videoId = extractYoutubeId(videoUrl)
        return if (videoId != null) "https://img.youtube.com/vi/$videoId/mqdefault.jpg" else ""
    }

    fun extractYoutubeId(url: String): String? {
        try {
            val uri = Uri.parse(url)
            val host = uri.host?.lowercase()

            return when {
                host != null && host.contains("youtu.be") -> {
                    uri.lastPathSegment
                }

                host != null && host.contains("youtube.com") -> {
                    uri.getQueryParameter("v")
                }

                url.contains("embed/") -> {
                    url.split("embed/")[1].split("?")[0]
                }

                else -> null
            }
        } catch (e: Exception) {
            return null
        }
    }
}