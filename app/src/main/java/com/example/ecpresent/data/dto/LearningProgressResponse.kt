package com.example.ecpresent.data.dto

data class LearningProgressResponse(
    val createdAt: String,
    val id: Int,
    val learning_id: Int,
    val status: String,
    val updatedAt: String,
    val user_id: Int
)