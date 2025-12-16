package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class Question(
    val id: Int,
    @SerializedName("presentation_id")
    val presentationId: Int,
    val answer: Answer?,
    val question: String,
    @SerializedName("time_limit_seconds")
    val timeLimitSeconds: Int,
    val createdAt: String,
    val updatedAt: String
)