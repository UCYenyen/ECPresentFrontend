package com.example.ecpresent.ui.uistates


import com.example.ecpresent.data.dto.PresentationAnalysisResponse
import com.example.ecpresent.ui.model.User
import com.example.ecpresent.ui.model.LearningProgress
import com.example.ecpresent.ui.model.Learning

sealed interface LoginUIState {
    object Initial : LoginUIState
    object Loading : LoginUIState
    data class Success(val data: User) : LoginUIState
    data class Error(val message: String) : LoginUIState
}

sealed interface LearningUIState {
    object Initial : LearningUIState
    object Loading : LearningUIState
    data class Success(val data: List<Learning>) : LearningUIState
    data class Error(val message: String) : LearningUIState
}

sealed interface LearningDetailsUIState {
    object Initial : LearningDetailsUIState
    object Loading : LearningDetailsUIState
    data class Success(val data: Learning, val isAdded : Boolean) : LearningDetailsUIState
    data class Error(val message: String) : LearningDetailsUIState
}

sealed interface LearningProgressUIState {
    object Initial : LearningProgressUIState
    object Loading : LearningProgressUIState
    data class Success(val data: List<LearningProgress>) : LearningProgressUIState
    data class Error(val message: String) : LearningProgressUIState
}

sealed interface LearningProgressDetailsUIState {
    object Initial : LearningProgressDetailsUIState
    object Loading : LearningProgressDetailsUIState
    data class Success(val data: LearningProgress) : LearningProgressDetailsUIState
    data class Error(val message: String) : LearningProgressDetailsUIState
}


sealed interface ProfileUIState {
    object Initial : ProfileUIState
    object Loading : ProfileUIState
    object LoggedOut : ProfileUIState
    data class Success(val data: User) : ProfileUIState
    data class Error(val message: String) : ProfileUIState
}

sealed interface UploadPresentationUIState {
    object Initial : UploadPresentationUIState
    object Loading : UploadPresentationUIState
    data class Success(val data: PresentationAnalysisResponse) : UploadPresentationUIState
    data class Error(val message: String) : UploadPresentationUIState

}