package com.example.ecpresent.data.dto

data class PresentationFeedbackResponse(
    val answer_audio_url: String,
    val audio_score: Int,
    val audio_suggestion: String,
    val expression: Int,
    val grade: String,
    val intonation: Int,
    val overall_score: Int,
    val posture: Int,
    val presentation_id: Int,
    val question: String,
    val status: String,
    val video_score: Double,
    val video_suggestion: String,
    val success: Boolean
)