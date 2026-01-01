package com.example.ecpresent.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecpresent.data.container.ServerContainer
import com.example.ecpresent.data.dto.LoginUserRequest
import com.example.ecpresent.data.dto.RegisterUserRequest
import com.example.ecpresent.data.dto.UpdateUserRequest
import com.example.ecpresent.data.local.DataStoreManager
import com.example.ecpresent.ui.model.toUser
import com.example.ecpresent.ui.uistates.LoginUIState
import com.example.ecpresent.ui.uistates.ProfileUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository = ServerContainer().serverAuthRepository
    private val dataStoreManager = DataStoreManager(application)
    private val _loginUIState = MutableStateFlow<LoginUIState>(LoginUIState.Initial)
    val loginUIState: StateFlow<LoginUIState> = _loginUIState.asStateFlow()

    private val _profileUIState = MutableStateFlow<ProfileUIState>(ProfileUIState.Initial)
    val profileUIState: StateFlow<ProfileUIState> = _profileUIState.asStateFlow()

    init {
        viewModelScope.launch {
            val token = dataStoreManager.tokenFlow.first()
            if (!token.isNullOrEmpty()) {
                getProfileById()
            } else{
                _loginUIState.value = LoginUIState.Initial
                _profileUIState.value = ProfileUIState.Initial
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
                        getProfileById(explicitToken = token)
                    } else {
                        _loginUIState.value = LoginUIState.Error("Login successful but no token received")
                    }
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

    fun getProfileById(explicitToken: String? = null){
        viewModelScope.launch {
            _profileUIState.value = ProfileUIState.Loading
            try {
                val token = explicitToken ?: dataStoreManager.tokenFlow.first()
                if (token.isNullOrEmpty()){
                    _loginUIState.value = LoginUIState.Initial
                    return@launch
                }

                val response = authRepository.getProfileById(token)

                if (response.isSuccessful && response.body() != null) {
                    val userResponse = response.body()!!.data.toUser()
                    val token = userResponse.token
                    if (token.isNotEmpty()) {
                        dataStoreManager.saveToken(token)
                    }
                    _loginUIState.value = LoginUIState.Success(userResponse)
                    _profileUIState.value = ProfileUIState.Success(userResponse)
                } else {
                    val errorBody = response.errorBody()?.string()
                    val errorMsg = "Fetch failed: ${response.code()} - $errorBody"
                    _profileUIState.value = ProfileUIState.Error(errorMsg)
                    _loginUIState.value = LoginUIState.Error(errorMsg)
                }

            } catch (e: Exception) {
                e.printStackTrace()
                _profileUIState.value = ProfileUIState.Error(e.message ?: "Unknown error")
            }

        }
    }

    // Di dalam AuthViewModel.kt

    fun updateAvatar(newAvatarId: Int, passwordUser: String) {
        viewModelScope.launch {
            // 1. Ambil Token
            val rawToken = dataStoreManager.tokenFlow.first()
            if (rawToken.isNullOrEmpty()) {
                _profileUIState.value = ProfileUIState.Error("Token kosong")
                return@launch
            }

            // 2. Ambil Data Lama (Email & Username)
            val currentState = _profileUIState.value

            if (currentState is ProfileUIState.Success) {
                // --- PERBAIKAN 1: Ganti .user jadi .data ---
                val oldData = currentState.data

                // 3. Gabungkan Data (Wajib isi semua sesuai request temanmu)
                val request = UpdateUserRequest(
                    avatarId = newAvatarId,
                    email = oldData.email ?: "",
                    username = oldData.username ?: "",
                    password = passwordUser
                )

                try {
                    // --- PERBAIKAN 2: Tambahkan tulisan "Bearer " manual ---
                    // Karena di Repository fungsi updateProfile-mu tidak otomatis nambahin Bearer
                    val formattedToken = "Bearer $rawToken"

                    val response = authRepository.updateProfile(formattedToken, request)

                    if (response.isSuccessful) {
                        getProfileById() // Refresh biar gambar berubah
                    } else {
                        val errorMsg = response.errorBody()?.string() ?: "Gagal update"
                        _profileUIState.value = ProfileUIState.Error(errorMsg)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    _profileUIState.value = ProfileUIState.Error(e.message ?: "Error")
                }
            } else {
                _profileUIState.value = ProfileUIState.Error("Data profil belum siap")
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
                        getProfileById(explicitToken = token)
                    } else {
                        _loginUIState.value = LoginUIState.Error("Register successful but no token received")
                    }

                } else {
                    val errorBody = response.errorBody()?.string()
                    _loginUIState.value =
                        LoginUIState.Error("Register failed: ${response.code()} - $errorBody")
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
                        getProfileById(explicitToken = token)
                    }

                } else {
                    val errorMsg = response.errorBody()?.string() ?: "Unknown Server Error"
                    _loginUIState.value =
                        LoginUIState.Error("Login failed: ${response.code()} - $errorMsg")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _loginUIState.value = LoginUIState.Error(e.localizedMessage ?: "Connection error")
            }
        }
    }

    fun logout(onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            dataStoreManager.clearToken()
            _loginUIState.value = LoginUIState.Initial
            _profileUIState.value = ProfileUIState.Initial
            onSuccess()
        }
    }

    fun resetLoginState() {
        _loginUIState.value = LoginUIState.Initial
    }
}