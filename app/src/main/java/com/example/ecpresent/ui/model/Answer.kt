package com.example.ecpresent.ui.model

data class Answer  (
    val id: Int,
    val question: Question,
    val audioUrl: String?,
    val score: String?,
    val suggestion: String?,
    val createdAt: String,
    val updatedAt: String
)