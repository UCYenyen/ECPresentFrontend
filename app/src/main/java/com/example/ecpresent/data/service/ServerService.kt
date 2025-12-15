package com.example.ecpresent.data.service

import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.dto.LearningResponseItem
import com.example.ecpresent.data.dto.LoginUserRequest
import com.example.ecpresent.data.dto.RegisterUserRequest
import com.example.ecpresent.data.dto.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ServerService {
    @POST("register")
    suspend fun register(
        @Body request: RegisterUserRequest
    ): Response<BaseResponse<UserResponse>>
    @POST("login")
    suspend fun login(
        @Body request: LoginUserRequest
    ): Response<BaseResponse<UserResponse>>
    @POST("guest")
    suspend fun guest(): Response<BaseResponse<UserResponse>>
    @GET("get-all-learnings")
    suspend fun getAllLearnings(): Response<BaseResponse<List<LearningResponseItem>>>
}