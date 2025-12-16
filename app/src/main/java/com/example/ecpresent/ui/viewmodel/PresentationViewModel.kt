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

class PresentationViewModel(application: Application) : AndroidViewModel(application) {
    private val presentationRepository = ServerContainer().serverPresentationRepository
    private val dataStoreManager = DataStoreManager(application)
    private val _uploadPresentationUIState = MutableStateFlow<UploadPresentationUIState>(UploadPresentationUIState.Initial)
    val uploadPresentationUIState: StateFlow<UploadPresentationUIState> = _uploadPresentationUIState.asStateFlow()

    fun uploadPresentation(fileUri: Uri, title: String) {
        viewModelScope.launch {
            _uploadPresentationUIState.value = UploadPresentationUIState.Loading
            try {
                val token = dataStoreManager.tokenFlow.first()
                if (token.isNullOrEmpty()) {
                    _uploadPresentationUIState.value = UploadPresentationUIState.Error("User not logged in")
                    return@launch
                }

                val response = presentationRepository.uploadPresentation(
                    token = token,
                    fileUri = fileUri,
                    context = getApplication(),
                    title = title
                )

                if (response.isSuccessful && response.body() != null) {
                    val analysisData = response.body()!!.data
                    _uploadPresentationUIState.value = UploadPresentationUIState.Success(analysisData)
                } else {
                    val errorBody = response.errorBody()?.string()
                    _uploadPresentationUIState.value =
                        UploadPresentationUIState.Error("Upload failed: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _uploadPresentationUIState.value = UploadPresentationUIState.Error(e.message ?: "Unknown error")
            }
        }
    }
    fun resetUploadState() {
        _uploadPresentationUIState.value = UploadPresentationUIState.Initial
    }
}