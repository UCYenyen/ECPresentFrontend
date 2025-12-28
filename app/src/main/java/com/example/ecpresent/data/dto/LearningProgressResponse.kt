package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class LearningProgressResponse(
    val createdAt: String,
    val id: String,
    @SerializedName("learning")
    val learning: LearningResponseItem,
    val status: String,
    val updatedAt: String,
    val user_id: Int
)