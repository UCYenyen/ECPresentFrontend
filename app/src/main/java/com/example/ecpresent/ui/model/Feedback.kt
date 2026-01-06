package com.example.ecpresent.ui.model

import com.example.ecpresent.enum.GradeEnum
import com.example.ecpresent.data.dto.PresentationFeedbackResponse as FeedbackDto
data class Feedback (
    val id: String,
    val presentationId: String,
    val expression: Double,
    val intonation: Double,
    val posture: Double,
    val videoScore: Double,
    val audioScore: Double,
    val overallRating: Float,
    val grade: GradeEnum,
    val videoSuggestion: String,
    val audioSuggestion: String,
    val personalNotes: String
)

fun FeedbackDto.toUiModel(): Feedback {
    return Feedback(
        id = this.id,
        presentationId = this.presentationId,
        expression = this.expression,
        intonation = this.intonation,
        posture = this.posture,
        videoScore = this.videoScore,

        audioScore = this.audioScore ?: 0.0,
        overallRating = (this.overallRating ?: 0.0).toFloat(),

        grade = try {
            if (this.grade != null) GradeEnum.valueOf(this.grade) else GradeEnum.E
        } catch (e: IllegalArgumentException) {
            GradeEnum.UNKNOWN
        },

        videoSuggestion = this.videoSuggestion,
        audioSuggestion = this.audioSuggestion ?: "No audio feedback available yet.",
        personalNotes = this.personalNotes ?: ""
    )
}

