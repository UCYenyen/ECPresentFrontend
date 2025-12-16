package com.example.ecpresent.data.dto

import com.example.ecpresent.ui.model.Learning
import com.google.gson.annotations.SerializedName

data class LearningProgressResponse(
    val createdAt: String,
    val id: Int,
    @SerializedName("learning")
    val learning: LearningResponseItem,
    val status: String,
    val updatedAt: String,
    val user_id: Int
)