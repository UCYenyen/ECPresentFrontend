package com.example.ecpresent.data.service

import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.dto.LoginUserRequest
import com.example.ecpresent.data.dto.RegisterUserRequest
import com.example.ecpresent.data.dto.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthService {
    @POST("api/register")
    suspend fun register(
        @Body request: RegisterUserRequest
    ): Response<BaseResponse<UserResponse>>

    @POST("api/login")
    suspend fun login(
        @Body request: LoginUserRequest
    ): Response<BaseResponse<UserResponse>>

    @POST("api/guest")
    suspend fun continueAsGuest(): Response<BaseResponse<UserResponse>>

    @GET("api/get-profile")
    suspend fun getProfileById(
        @Header("Authorization") token: String
    ): Response<BaseResponse<UserResponse>>
}