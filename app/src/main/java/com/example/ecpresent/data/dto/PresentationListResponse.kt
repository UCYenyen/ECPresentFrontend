package com.example.ecpresent.data.dto

data class PresentationListResponse(
    val createdAt: String,
    val id: Int,
    val status: String,
    val title: String,
    val updatedAt: String,
    val user_id: Int,
    val video_url: String,
    val success: Boolean
)