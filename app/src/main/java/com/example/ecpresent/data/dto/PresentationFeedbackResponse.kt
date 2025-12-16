package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class PresentationFeedbackResponse(
    val id: Int,
    @SerializedName("presentation_id")
    val presentationId: Int,

    val expression: Float,
    val intonation: Float,
    val posture: Float,

    @SerializedName("video_score")
    val videoScore: Float,
    @SerializedName("audio_score")
    val audioScore: Float?,
    @SerializedName("overall_rating")
    val overallRating: Float?,
    val grade: String?,

    @SerializedName("video_suggestion")
    val videoSuggestion: String,
    @SerializedName("audio_suggestion")
    val audioSuggestion: String?,

    val question: String,

    @SerializedName("audio_url")
    val audioUrl: String?,
    val status: String,

    val createdAt: String,
    val updatedAt: String,

)