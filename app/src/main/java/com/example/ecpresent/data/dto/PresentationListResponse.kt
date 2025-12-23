package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class PresentationListResponse(
    val createdAt: String,
    val id: Int,
    val status: String,
    val title: String,
    val updatedAt: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("video_url")
    val videoUrl: String,
    @SerializedName("personal_notes")
    val personalNotes: String?,
    val success: Boolean
)