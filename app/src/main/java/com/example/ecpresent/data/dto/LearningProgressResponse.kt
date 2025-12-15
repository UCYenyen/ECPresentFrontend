package com.example.ecpresent.data.dto

import com.example.ecpresent.ui.model.Learning

data class LearningProgressResponse(
    val createdAt: String,
    val id: Int,
    val learning: LearningResponseItem,
    val status: String,
    val updatedAt: String,
    val user_id: Int
)