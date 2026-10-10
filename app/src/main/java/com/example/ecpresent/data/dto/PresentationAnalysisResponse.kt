package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class PresentationAnalysisResponse(
    val status: String,
    @SerializedName("failure_reason")
    val failureReason: String?,
    val feedback: PresentationFeedbackResponse?,
    val question: Question?,
    val processing: ProcessingInfo?
)
