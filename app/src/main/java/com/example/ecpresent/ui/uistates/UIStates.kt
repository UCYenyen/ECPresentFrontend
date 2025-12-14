package com.example.ecpresent.ui.uistates


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
    data class Success(val data: Learning) : LearningUIState
    data class Error(val message: String) : LearningUIState
}

sealed interface LearningProgressUIState {
    object Initial : LearningProgressUIState
    object Loading : LearningProgressUIState
    data class Success(val data: LearningProgress) : LearningProgressUIState
    data class Error(val message: String) : LearningUIState
}