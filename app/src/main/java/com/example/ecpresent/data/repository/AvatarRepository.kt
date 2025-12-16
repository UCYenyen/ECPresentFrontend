package com.example.ecpresent.data.repository

import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.dto.LearningResponseItem
import com.example.ecpresent.data.dto.LoginUserRequest
import com.example.ecpresent.data.dto.RegisterUserRequest
import com.example.ecpresent.data.dto.UserResponse
import com.example.ecpresent.data.service.AuthService
import com.example.ecpresent.data.service.AvatarService
import retrofit2.Response

class AvatarRepository(private val service: AvatarService) {


    suspend fun getAllAvatars() = service.getAllAvatars()

    suspend fun getAvatarById(id: Int) = service.getAvatarById(id)

}