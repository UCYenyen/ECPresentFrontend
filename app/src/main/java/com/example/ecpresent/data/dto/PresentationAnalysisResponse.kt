package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class PresentationAnalysisResponse(

    val feedback: PresentationFeedbackResponse?,
    val question: Question?,
    val status: String?,
    val success: Boolean,
    @SerializedName("failure_reason")
    val failureReason: String? = null,
    val processing: ProcessingInfo? = null
)