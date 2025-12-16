package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class Answer(
    val id: Int,
    @SerializedName("question_id")
    val questionId: Int,
    @SerializedName("audio_url")
    val audioUrl: String?,
    val score: Float?,
    val suggestion: String?,
    val createdAt: String,
    val updatedAt: String
)