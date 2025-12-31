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
import retrofit2.http.Path

interface AvatarService {
    @GET("api/avatar/{id}")
    suspend fun getAvatarById(@Path("id") id: Int)
    @GET("api/get-user-avatar/{id}")
    suspend fun getUserAvatar(@Path("id") id: Int)
}