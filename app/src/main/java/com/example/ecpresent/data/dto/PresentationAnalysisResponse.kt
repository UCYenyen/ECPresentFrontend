package com.example.ecpresent.data.dto

data class PresentationAnalysisResponse(
    val id: Int,

    val title: String?,

    // Anak-anaknya (bisa ada, bisa null)
    val feedback: PresentationFeedbackResponse?,
    val question: Question?,

    // Status tambahan
    val status: String?,
    val success: Boolean
)