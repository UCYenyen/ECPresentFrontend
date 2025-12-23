package com.example.ecpresent.ui.viewmodel

import android.app.Application
import android.net.Uri
import android.os.CountDownTimer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecpresent.data.container.ServerContainer
import com.example.ecpresent.data.dto.Answer
import com.example.ecpresent.data.dto.LoginUserRequest
import com.example.ecpresent.data.dto.PresentationAnalysisResponse
import com.example.ecpresent.data.dto.PresentationFeedbackResponse
import com.example.ecpresent.data.dto.RegisterUserRequest
import com.example.ecpresent.data.local.DataStoreManager
import com.example.ecpresent.data.repository.PresentationRepository
import com.example.ecpresent.ui.model.Avatar
import com.example.ecpresent.ui.model.User
import com.example.ecpresent.ui.model.Learning
import com.example.ecpresent.ui.model.LearningProgress
import com.example.ecpresent.ui.model.toLearning
import com.example.ecpresent.ui.model.toLearningProgress
import com.example.ecpresent.ui.uistates.FeedbackUIState
import com.example.ecpresent.ui.uistates.LearningDetailsUIState
import com.example.ecpresent.ui.uistates.LearningProgressUIState
import com.example.ecpresent.ui.uistates.LearningUIState
import com.example.ecpresent.ui.uistates.LoginUIState
import com.example.ecpresent.ui.uistates.ProfileUIState
import com.example.ecpresent.ui.uistates.QnAUIState
import com.example.ecpresent.ui.uistates.UploadPresentationUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.io.File

class PresentationViewModel(application: Application) : AndroidViewModel(application) {
    private val presentationRepository = ServerContainer().serverPresentationRepository
    private val dataStoreManager = DataStoreManager(application)
    private val _uploadPresentationUIState = MutableStateFlow<UploadPresentationUIState>(UploadPresentationUIState.Initial)
    val uploadPresentationUIState: StateFlow<UploadPresentationUIState> = _uploadPresentationUIState.asStateFlow()

    private val _qnaState = MutableStateFlow<QnAUIState>(QnAUIState.Idle)
    val qnaState = _qnaState.asStateFlow()

    private val _feedbackState = MutableStateFlow<FeedbackUIState>(FeedbackUIState.Idle)
    val feedbackState = _feedbackState.asStateFlow()

    private val _isRecording = MutableStateFlow(false)
    val isRecording = _isRecording.asStateFlow()

    private val _feedbackNotes = MutableStateFlow("")
    val feedbackNotes = _feedbackNotes.asStateFlow()

    private val _timer = MutableStateFlow(10)
    val timer = _timer.asStateFlow()
    private var countDownTimer: CountDownTimer? = null

    var activePresentationId: Int? = null
        private set
    var activeQuestion: String? = null
        private set

    var activeFeedbackData: PresentationFeedbackResponse? = null
        private set
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

    fun startTimer(onFinish: () -> Unit) {
        _timer.value = 10
        countDownTimer?.cancel()
        countDownTimer = object : CountDownTimer(10000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timer.value = (millisUntilFinished / 1000).toInt()
            }
            override fun onFinish() {
                _timer.value = 0
                onFinish()
            }
        }.start()
    }

    fun stopTimer() {
        countDownTimer?.cancel()
    }

    fun submitAnswer(audioFile: File) {
        stopTimer()
        _isRecording.value = false

        viewModelScope.launch {
            _qnaState.value = QnAUIState.Submitting
            try {
                val token = dataStoreManager.tokenFlow.first() ?: return@launch
                val id = activePresentationId?.toString() ?: return@launch

                val response = presentationRepository.submitAnswer(token, id, audioFile)

                if (response.isSuccessful && response.body()?.data != null) {
                    _qnaState.value = QnAUIState.AnswerScored(response.body()!!.data)
                } else {
                    _qnaState.value = QnAUIState.Error("Gagal: ${response.code()}")
                }
            } catch (e: Exception) {
                _qnaState.value = QnAUIState.Error(e.message ?: "Error")
            }
        }
    }

    fun getFinalFeedback() {
        viewModelScope.launch {
            _feedbackState.value = FeedbackUIState.Loading
            try {
                val token = dataStoreManager.tokenFlow.first() ?: return@launch
                val id = activePresentationId?.toString() ?: return@launch

                val response = presentationRepository.getFinalFeedback(token, id)

                if (response.isSuccessful && response.body()?.data != null) {
                    val data = response.body()!!.data
                    _feedbackState.value = FeedbackUIState.Success(data)
                    // Reset notes jika ada data baru
                    _feedbackNotes.value = data.personalNotes ?: "" // Jika Anda punya field ini di DTO feedback
                } else {
                    _feedbackState.value = FeedbackUIState.Error("Gagal memuat feedback")
                }
            } catch (e: Exception) {
                _feedbackState.value = FeedbackUIState.Error(e.message ?: "Error")
            }
        }
    }

    fun onNotesChanged(newNotes: String) {
        _feedbackNotes.value = newNotes
    }

    fun updateNotes() {
        viewModelScope.launch {
            try {
                val token = dataStoreManager.tokenFlow.first() ?: return@launch
                val id = activePresentationId ?: return@launch // Use stored ID
                val notes = _feedbackNotes.value // Use stored notes from StateFlow

                val response = presentationRepository.updateNotes(token, id, notes)
                if (response.isSuccessful) {
                    _feedbackState.value = FeedbackUIState.NotesUpdated
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 2. Delete Presentation (Uses activePresentationId)
    fun deletePresentation(onSuccess: () -> Unit) {
        viewModelScope.launch {
            _feedbackState.value = FeedbackUIState.Loading
            try {
                val token = dataStoreManager.tokenFlow.first() ?: return@launch
                val id = activePresentationId ?: return@launch // Use stored ID

                val response = presentationRepository.deletePresentation(token, id)

                if (response.isSuccessful) {
                    _feedbackState.value = FeedbackUIState.Deleted
                    resetState()
                    onSuccess()
                } else {
                    _feedbackState.value = FeedbackUIState.Error("Gagal menghapus")
                }
            } catch (e: Exception) {
                _feedbackState.value = FeedbackUIState.Error(e.message ?: "Error")
            }
        }
    }
    fun setRecordingState(isRecording: Boolean) {
        _isRecording.value = isRecording
    }

    fun resetState() {
        _uploadPresentationUIState.value = UploadPresentationUIState.Initial
        _qnaState.value = QnAUIState.Idle
        _feedbackState.value = FeedbackUIState.Idle
    }
}