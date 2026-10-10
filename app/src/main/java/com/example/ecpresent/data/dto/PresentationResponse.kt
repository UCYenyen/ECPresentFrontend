package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class PresentationResponse(
    val id: Int,
    @SerializedName("user_id")
    val userId: Int,
    val title: String,
    @SerializedName("video_url")
    val videoUrl: String,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)
