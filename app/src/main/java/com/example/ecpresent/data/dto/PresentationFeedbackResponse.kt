package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class PresentationFeedbackResponse(
    val id: String,
    @SerializedName("presentation_id")
    val presentationId: String,

    val expression: Double,
    val intonation: Double,
    val posture: Double,

    @SerializedName("video_score")
    val videoScore: Double,
    @SerializedName("audio_score")
    val audioScore: Double?,
    @SerializedName("overall_rating")
    val overallRating: Double?,
    val grade: String?,

    @SerializedName("video_suggestion")
    val videoSuggestion: String,
    @SerializedName("audio_suggestion")
    val audioSuggestion: String?,

    val question: String,

    @SerializedName("audio_url")
    val audioUrl: String?,
    val status: String,
    @SerializedName("personal_notes")
    val personalNotes: String?,
    val createdAt: String,
    val updatedAt: String,

)