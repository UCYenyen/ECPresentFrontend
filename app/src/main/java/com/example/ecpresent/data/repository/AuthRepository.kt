package com.example.ecpresent.data.repository

import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.dto.LearningResponseItem
import com.example.ecpresent.data.dto.LoginUserRequest
import com.example.ecpresent.data.dto.RegisterUserRequest
import com.example.ecpresent.data.dto.UserResponse
import com.example.ecpresent.data.service.AuthService
import retrofit2.Response

class AuthRepository(private val service: AuthService) {
    suspend fun register(request: RegisterUserRequest): Response<BaseResponse<UserResponse>> {
        return service.register(request)
    }

    suspend fun login(request: LoginUserRequest): Response<BaseResponse<UserResponse>> {
        return service.login(request)
    }

    suspend fun continueAsGuest(): Response<BaseResponse<UserResponse>> {
        return service.continueAsGuest()
    }
}