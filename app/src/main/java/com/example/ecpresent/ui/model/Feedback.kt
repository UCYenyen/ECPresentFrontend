package com.example.ecpresent.ui.model

data class Feedback (
    val id: Int,
    val presentation: Presentation,
    val expression: Float,
    val intonation: Float,
    val posture: Float,
    val videoScore: Float,
    val audioScore: Float,
    val overallRating: Float,
    val grade: GradeEnum,
    val videoSuggestion: String,
    val audioSuggestion: String?,
    val createdAt: String,
    val updatedAt: String
)

enum class GradeEnum {
    A,
    B,
    C,
    D,
    E,
    F,
    T
}