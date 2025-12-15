package com.example.ecpresent.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecpresent.data.container.ServerContainer
import com.example.ecpresent.data.dto.LoginUserRequest
import com.example.ecpresent.data.dto.RegisterUserRequest
import com.example.ecpresent.ui.model.Avatar
import com.example.ecpresent.ui.model.User
import com.example.ecpresent.ui.uistates.LearningProgressUIState
import com.example.ecpresent.ui.uistates.LearningUIState
import com.example.ecpresent.ui.uistates.LoginUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ViewModel : ViewModel() {

    private val repository = ServerContainer().serverRepository

    private val _learningUIState = MutableStateFlow<LearningUIState>(LearningUIState.Initial)
    val learningUIState: StateFlow<LearningUIState> = _learningUIState.asStateFlow()

    private val _learningProgressUIState = MutableStateFlow<LearningProgressUIState>(LearningProgressUIState.Initial)
    val learningProgressUIState: StateFlow<LearningProgressUIState> = _learningProgressUIState.asStateFlow()

    private val _loginUIState = MutableStateFlow<LoginUIState>(LoginUIState.Initial)
    val loginUIState: StateFlow<LoginUIState> = _loginUIState.asStateFlow()

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _loginUIState.value = LoginUIState.Loading
            try {
                val request = LoginUserRequest(email, pass)
                val response = repository.login(request)

                if (response.isSuccessful && response.body() != null) {
                    val userResponse = response.body()!!.data
                    val user = User(
                        id = userResponse.id.toString(),
                        username = userResponse.username,
                        email = userResponse.email,
                        imageUrl = "",
                        createdAt = "2",
                        updatedAt = "2",
                        avatar = Avatar(id="2", imageUrl = "", createdAt = "2", updatedAt = "2"),
                        token = userResponse.token
                    )
                    _loginUIState.value = LoginUIState.Success(user)
                } else {
                    _loginUIState.value = LoginUIState.Error("Login failed: ${response.code()}")
                }
            } catch (e: Exception) {
                _loginUIState.value = LoginUIState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun register(username: String, email: String, pass: String, confirmPass: String) {
        viewModelScope.launch {
            if (pass != confirmPass) {
                _loginUIState.value = LoginUIState.Error("Password mismatch")
                return@launch
            }

            _loginUIState.value = LoginUIState.Loading
            try {
                val request = RegisterUserRequest(email, pass, username)
                val response = repository.register(request)

                if (response.isSuccessful && response.body() != null) {
                    val userResponse = response.body()!!.data
                    val user = User(
                        id = userResponse.id.toString(),
                        username = userResponse.username,
                        email = userResponse.email,
                        imageUrl = "",
                        createdAt = "2",
                        updatedAt = "2",
                        avatar = Avatar(id="2", imageUrl = "", createdAt = "2", updatedAt = "2"),
                        token = userResponse.token
                    )
                    _loginUIState.value = LoginUIState.Success(user)
                } else {
                    _loginUIState.value = LoginUIState.Error("Register failed: ${response.code()}")
                }
            } catch (e: Exception) {
                _loginUIState.value = LoginUIState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun guestLogin() {
        viewModelScope.launch {
            _loginUIState.value = LoginUIState.Loading
            try {
                val response = repository.guest()
                if (response.isSuccessful && response.body() != null) {
                    val userResponse = response.body()!!.data
                    val user = User(
                        id = userResponse.id.toString(),
                        username = userResponse.username,
                        email = userResponse.email,
                        imageUrl = "",
                        createdAt = "2",
                        updatedAt = "2",
                        avatar = Avatar(id="2", imageUrl = "", createdAt = "2", updatedAt = "2"),
                        token = userResponse.token
                    )
                    _loginUIState.value = LoginUIState.Success(user)
                } else {
                    _loginUIState.value = LoginUIState.Error("Guest login failed")
                }
            } catch (e: Exception) {
                _loginUIState.value = LoginUIState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun resetLoginState() {
        _loginUIState.value = LoginUIState.Initial
    }
}