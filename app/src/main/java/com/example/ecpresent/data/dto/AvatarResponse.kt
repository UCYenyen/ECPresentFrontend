package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class AvatarResponse(
    val createdAt: String,
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    val updatedAt: String
)