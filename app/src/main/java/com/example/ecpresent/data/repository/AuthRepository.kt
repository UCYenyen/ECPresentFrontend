package com.example.ecpresent.data.repository

import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.dto.LoginUserRequest
import com.example.ecpresent.data.dto.RegisterUserRequest
import com.example.ecpresent.data.dto.UpdateUserRequest
import com.example.ecpresent.data.dto.UserResponse
import com.example.ecpresent.data.service.AuthService
import retrofit2.Response

class AuthRepository(private val service: AuthService) {
    suspend fun register(request: RegisterUserRequest): Response<BaseResponse<UserResponse>> {
        return service.register(request)
    }

    suspend fun registerUserFromGuest(token: String, request: RegisterUserRequest): Response<BaseResponse<UserResponse>> {
        val formattedToken = "Bearer $token"
        return service.registerFromGuest( formattedToken,request)
    }

    suspend fun login(request: LoginUserRequest): Response<BaseResponse<UserResponse>> {
        return service.login(request)
    }

    suspend fun continueAsGuest(): Response<BaseResponse<UserResponse>> {
        return service.continueAsGuest()
    }

    suspend fun getProfileById(token: String): Response<BaseResponse<UserResponse>>{
        val formattedToken = "Bearer $token"
        return service.getProfileById(token = formattedToken)
    }

    suspend fun updateProfile(token: String, request: UpdateUserRequest): Response<BaseResponse<UserResponse>> {
        // GANTI 'authService' JADI 'service'
        return service.updateProfile(token, request)
    }
}