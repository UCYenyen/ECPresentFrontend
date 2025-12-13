package com.example.ecpresent.data.dto

data class PresentationAnalysisResponse(
    val feedback: Feedback,
    val question: Question,
    val status: String,
    val success: Boolean
)