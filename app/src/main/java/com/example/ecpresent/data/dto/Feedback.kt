package com.example.ecpresent.data.dto

data class Feedback(
    val audio_score: Int,
    val audio_suggestion: String,
    val createdAt: String,
    val expression: Int,
    val grade: String,
    val id: Int,
    val intonation: Int,
    val overall_rating: Int,
    val posture: Int,
    val presentation_id: Int,
    val updatedAt: String,
    val video_score: Double,
    val video_suggestion: String
)