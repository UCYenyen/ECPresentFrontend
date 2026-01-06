package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class PresentationListResponse(
    val id: Int,
    val status: String,
    val title: String,

    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("video_url")
    val videoUrl: String,

    val createdAt: String,
    val updatedAt: String,
)