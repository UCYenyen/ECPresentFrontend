package com.example.ecpresent.data.dto

data class Answer(
    val audio_url: String,
    val createdAt: String,
    val id: Int,
    val question_id: Int,
    val score: Int,
    val suggestion: String,
    val updatedAt: String
)