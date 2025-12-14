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

    // Backend: publicRouter.post("/register", UserController.register)
    @POST("register")
    suspend fun register(
        @Body request: RegisterUserRequest
    ): Response<BaseResponse<UserResponse>>

    // Backend: publicRouter.post("/login", UserController.login)
    @POST("login")
    suspend fun login(
        @Body request: LoginUserRequest
    ): Response<BaseResponse<UserResponse>>

    // Backend: publicRouter.post("/guest", UserController.guest)
    @POST("guest")
    suspend fun guest(): Response<BaseResponse<UserResponse>>

}