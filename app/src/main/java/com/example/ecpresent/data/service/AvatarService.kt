package com.example.ecpresent.data.service

import com.example.ecpresent.data.dto.AvatarResponse
import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.dto.LearningResponseItem
import com.example.ecpresent.data.dto.LoginUserRequest
import com.example.ecpresent.data.dto.RegisterUserRequest
import com.example.ecpresent.data.dto.UserResponse
import com.example.ecpresent.ui.model.Avatar
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AvatarService {

    @GET("avatar")
    suspend fun getAllAvatars(@Header("Authorization") token : String): Response<BaseResponse<List<AvatarResponse>>>
    @GET("avatar/{id}")
    suspend fun getAvatarById(@Header("Authorization") token : String, @Path("id") id: Int): Response<BaseResponse<AvatarResponse>>

    @PUT("avatar/{id}")
    suspend fun updateAvatar(@Header("Authorization") token : String, @Path("id") id: Int, @Body request: AvatarResponse): Response<BaseResponse<AvatarResponse>>

    @DELETE ("avatar/{id}")
    suspend fun deleteAvatar(@Header("Authorization") token : String, @Path("id") id: Int): Response<BaseResponse<AvatarResponse>>
}