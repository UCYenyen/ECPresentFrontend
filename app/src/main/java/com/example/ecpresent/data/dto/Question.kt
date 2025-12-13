package com.example.ecpresent.data.dto

data class Question(
    val answer: Answer,
    val createdAt: String,
    val id: Int,
    val presentation_id: Int,
    val question: String,
    val time_limit_seconds: Int,
    val updatedAt: String
)