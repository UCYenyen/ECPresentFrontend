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
import com.example.ecpresent.ui.uistates.LoginUIState
import com.example.ecpresent.ui.uistates.ProfileUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val authRepository = ServerContainer().serverAuthRepository
    private val avatarRepository = ServerContainer().serverAvatarRepository
    private val dataStoreManager = DataStoreManager(application)
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
            }else{
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
                    _loginUIState.value =
                        LoginUIState.Error("Login failed: ${response.code()} - $errorBody")
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
                val request =
                    RegisterUserRequest(username = username, email = email, password = pass)
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

    fun getUserProfile(){
        viewModelScope.launch {
            _profileUIState.value = ProfileUIState.Loading
            try {
                val userProfile =
                    authRepository.getUserProfile(dataStoreManager.tokenFlow.first()!!)

                if (userProfile.isSuccessful && userProfile.body() != null) {
                    val userResponse = userProfile.body()!!.data

                    val user = User(
                        id = userResponse.id.toString(),
                        username = userResponse.username ?: "",
                        email = userResponse.email ?: "",
                        imageUrl = userResponse.imageUrl ?: "",
                        createdAt = userResponse.createdAt ?: "",
                        updatedAt = userResponse.updatedAt ?: "",
                        role = "USER",
                        avatar = Avatar(id = "2", imageUrl = "", createdAt = "2", updatedAt = "2"),
                        token = userResponse.token
                    )
                    _profileUIState.value = ProfileUIState.Success(user)

                } else {
                    val errorBody = userProfile.errorBody()?.string()
                    _profileUIState.value =
                        ProfileUIState.Error("Get User Data failed: ${userProfile.code()} - $errorBody")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _profileUIState.value = ProfileUIState.Error(e.message ?: "Unknown error")
            }
        }
    }
    fun uploadAvatar(imageUri: android.net.Uri) {
        viewModelScope.launch {
            _profileUIState.value = ProfileUIState.Loading
            try {
                // Ambil token
                val token = dataStoreManager.tokenFlow.first()
                if (token.isNullOrEmpty()) {
                    _profileUIState.value = ProfileUIState.Error("Token tidak ditemukan")
                    return@launch
                }

                val context = getApplication<Application>().applicationContext
                val file = uriToFile(imageUri, context)

                val requestFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
                val body = MultipartBody.Part.createFormData("avatar", file.name, requestFile)

                val response = avatarRepository.uploadCustomAvatar(token, body)

                if (response.isSuccessful) {
                    // Refresh data profile biar gambarnya update di UI
                    getUserProfile()
                } else {
                    _profileUIState.value = ProfileUIState.Error("Gagal upload: ${response.message()}")
                }
            } catch (e: Exception) {
                _profileUIState.value = ProfileUIState.Error("Error: ${e.message}")
            }
        }
    }

    // 2. Fungsi Delete/Reset Avatar (Balik ke Random)
    fun deleteAvatar(avatarId: Int) {
        viewModelScope.launch {
            _profileUIState.value = ProfileUIState.Loading
            try {
                val token = dataStoreManager.tokenFlow.first()
                if (!token.isNullOrEmpty()) {
                    val response = avatarRepository.deleteAvatar(token, avatarId)

                    if (response.isSuccessful) {
                        getUserProfile() // Refresh lagi
                    } else {
                        _profileUIState.value = ProfileUIState.Error("Gagal reset avatar")
                    }
                }
            } catch (e: Exception) {
                _profileUIState.value = ProfileUIState.Error("Error: ${e.message}")
            }
        }
    }

    // Helper: Copas fungsi ini di paling bawah class atau di file Utils terpisah
    private fun uriToFile(selectedImg: android.net.Uri, context: android.content.Context): File {
        val contentResolver = context.contentResolver
        val myFile = File.createTempFile("temp_image", ".jpg", context.cacheDir)

        val inputStream = contentResolver.openInputStream(selectedImg) as java.io.InputStream
        val outputStream = java.io.FileOutputStream(myFile)

        inputStream.copyTo(outputStream)
        inputStream.close()
        outputStream.close()

        return myFile
    }
}