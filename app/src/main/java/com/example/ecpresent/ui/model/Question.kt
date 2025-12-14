package com.example.ecpresent.ui.model

data class Question(
    val id: Int,
    val presentation: Presentation,
    val question: String,
    val timeLimitInSeconds: String,
    val createdAt: String,
    val updatedAt: String
)