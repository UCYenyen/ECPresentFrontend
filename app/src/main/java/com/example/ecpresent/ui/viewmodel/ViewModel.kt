package com.example.ecpresent.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecpresent.data.container.ServerContainer
import com.example.ecpresent.data.dto.LoginUserRequest
import com.example.ecpresent.data.dto.RegisterUserRequest
import com.example.ecpresent.data.local.DataStoreManager
import com.example.ecpresent.ui.model.Avatar
import com.example.ecpresent.ui.model.User
import com.example.ecpresent.ui.uistates.LearningProgressUIState
import com.example.ecpresent.ui.uistates.LearningUIState
import com.example.ecpresent.ui.uistates.LoginUIState
import com.example.ecpresent.ui.uistates.ProfileUIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository = ServerContainer().serverAuthRepository
    private val dataStoreManager = DataStoreManager(application)

    private val _learningUIState = MutableStateFlow<LearningUIState>(LearningUIState.Initial)
    val learningUIState: StateFlow<LearningUIState> = _learningUIState.asStateFlow()

    private val _learningProgressUIState = MutableStateFlow<LearningProgressUIState>(LearningProgressUIState.Initial)
    val learningProgressUIState: StateFlow<LearningProgressUIState> = _learningProgressUIState.asStateFlow()

    private val _loginUIState = MutableStateFlow<LoginUIState>(LoginUIState.Initial)
    val loginUIState: StateFlow<LoginUIState> = _loginUIState.asStateFlow()

    private val _profileUIState = MutableStateFlow<ProfileUIState>(ProfileUIState.Initial)
    val profileUIState: StateFlow<ProfileUIState> = _profileUIState.asStateFlow()

    init {
        viewModelScope.launch {
            val token = dataStoreManager.tokenFlow.first()
            if (!token.isNullOrEmpty()) {
                val user = User(
                    id = "",
                    username = "",
                    email = "",
                    imageUrl = "",
                    createdAt = "",
                    updatedAt = "",
                    role = "USER",
                    avatar = null,
                    token = token
                )
                _loginUIState.value = LoginUIState.Success(user)
                _profileUIState.value = ProfileUIState.Success(user)
            }
        }
    }

    fun login(email: String, pass: String) {
        viewModelScope.launch {
            _loginUIState.value = LoginUIState.Loading
            try {
                val request = LoginUserRequest(email, pass)
                val response = authRepository.login(request)

                if (response.isSuccessful && response.body() != null) {
                    val userResponse = response.body()!!.data
                    val token = userResponse.token

                    if (token.isNotEmpty()) {
                        dataStoreManager.saveToken(token)
                    }

                    val user = User(
                        id = userResponse.id.toString(),
                        username = userResponse.username ?: "",
                        email = userResponse.email ?: "",
                        imageUrl = userResponse.imageUrl ?: "",
                        createdAt = userResponse.createdAt ?: "",
                        updatedAt = userResponse.updatedAt ?: "",
                        role = userResponse.userRole ?: "USER",
                        avatar = Avatar(id = "2", imageUrl = "", createdAt = "2", updatedAt = "2"),
                        token = token
                    )
                    _loginUIState.value = LoginUIState.Success(user)
                    _profileUIState.value = ProfileUIState.Success(user)
                } else {
                    val errorBody = response.errorBody()?.string()
                    _loginUIState.value = LoginUIState.Error("Login failed: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                e.printStackTrace()
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
                val request = RegisterUserRequest(username = username, email = email, password = pass)
                val response = authRepository.register(request)

                if (response.isSuccessful && response.body() != null) {
                    val userResponse = response.body()!!.data
                    val token = userResponse.token

                    if (token.isNotEmpty()) {
                        dataStoreManager.saveToken(token)
                    }

                    val user = User(
                        id = userResponse.id.toString(),
                        username = userResponse.username ?: "",
                        email = userResponse.email ?: "",
                        imageUrl = userResponse.imageUrl ?: "",
                        createdAt = userResponse.createdAt ?: "",
                        updatedAt = userResponse.updatedAt ?: "",
                        role = "USER",
                        avatar = Avatar(id = "2", imageUrl = "", createdAt = "2", updatedAt = "2"),
                        token = token
                    )
                    _loginUIState.value = LoginUIState.Success(user)
                    _profileUIState.value = ProfileUIState.Success(user)
                } else {
                    val errorBody = response.errorBody()?.string()
                    _loginUIState.value = LoginUIState.Error("Register failed: ${response.code()} - $errorBody")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _loginUIState.value = LoginUIState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun continueAsGuest() {
        viewModelScope.launch {
            _loginUIState.value = LoginUIState.Loading
            try {
                val response = authRepository.continueAsGuest()

                if (response.isSuccessful && response.body() != null) {
                    val userResponse = response.body()!!.data
                    val token = userResponse.token

                    if (token.isNotEmpty()) {
                        dataStoreManager.saveToken(token)
                    }

                    val user = User(
                        id = userResponse.id.toString(),
                        username = userResponse.username ?: "Guest",
                        email = userResponse.email ?: "",
                        imageUrl = userResponse.imageUrl ?: "",
                        createdAt = userResponse.createdAt ?: "",
                        updatedAt = userResponse.updatedAt ?: "",
                        avatar = Avatar(id = "1", imageUrl = "", createdAt = "", updatedAt = ""),
                        role = "GUEST",
                        token = token
                    )
                    _loginUIState.value = LoginUIState.Success(user)
                    _profileUIState.value = ProfileUIState.Success(user)
                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Unknown Server Error"
                    _loginUIState.value = LoginUIState.Error("Login failed: ${response.code()} - $errorMsg")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _loginUIState.value = LoginUIState.Error(e.localizedMessage ?: "Connection error")
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearToken()
            _learningUIState.value = LearningUIState.Initial
            _learningProgressUIState.value = LearningProgressUIState.Initial
            _loginUIState.value = LoginUIState.Initial
            _profileUIState.value = ProfileUIState.LoggedOut
        }
    }

    fun resetLoginState() {
        _loginUIState.value = LoginUIState.Initial
    }
}