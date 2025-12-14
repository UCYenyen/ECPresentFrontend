package com.example.ecpresent.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecpresent.data.container.ServerContainer
import com.example.ecpresent.ui.uistates.LearningUIState
import com.example.ecpresent.ui.uistates.LoginUIState
import com.example.ecpresent.ui.uistates.LearningProgressUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {

    private val repository = ServerContainer().serverRepository
    private val _learningUIState = MutableStateFlow<LearningUIState>(LearningUIState.Initial)
    val learningUIState: StateFlow<LearningUIState> = _learningUIState.asStateFlow()
    private val _learningProgressUIState = MutableStateFlow<LearningProgressUIState>(
        LearningProgressUIState.Initial
    )
    val learningProgressUIState: StateFlow<LearningProgressUIState> =
        _learningProgressUIState.asStateFlow()

    private val _loginUIState = MutableStateFlow<LoginUIState>(
        LoginUIState.Initial
    )
    val loginUIState: StateFlow<LoginUIState> =
        _loginUIState.asStateFlow()



    fun getLoginData() {
        viewModelScope.launch {
            _loginUIState.value = LoginUIState.Loading
//            try {
//                val result = repository.getArtist()
//                if (result == null) {
//                    _artistUIState.value = ArtistUIState.Error("Artist not found")
//                    return@launch
//                }
//                _artistUIState.value = ArtistUIState.Success(result)
//            } catch (e: Exception) {
//                if (e.message == "timeout" || e.message == "Unable to resolve host \"www.theaudiodb.com\": No address associated with hostname") {
//                    _artistUIState.value = ArtistUIState.Error("Error: Tidak ada koneksi internet")
//                    return@launch
//                }
//                _artistUIState.value =
//                    ArtistUIState.Error(e.message ?: "Tidak ada koneksi internet")
//            }
        }
    }
}
