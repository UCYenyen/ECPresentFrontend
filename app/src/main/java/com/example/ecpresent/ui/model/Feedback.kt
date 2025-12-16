package com.example.ecpresent.ui.model

import com.example.ecpresent.enum.GradeEnum
import com.example.ecpresent.data.dto.PresentationFeedbackResponse as FeedbackDto
data class Feedback (
    val id: Int,
    val presentationId: Int,
    val expression: Float,
    val intonation: Float,
    val posture: Float,
    val videoScore: Float,
    val audioScore: Float,
    val overallRating: Float,
    val grade: GradeEnum,
    val videoSuggestion: String,
    val audioSuggestion: String
)

fun FeedbackDto.toUiModel(): Feedback {
    return Feedback(
        id = this.id,
        presentationId = this.presentationId,
        expression = this.expression,
        intonation = this.intonation,
        posture = this.posture,
        videoScore = this.videoScore,

        audioScore = this.audioScore ?: 0f,
        overallRating = this.overallRating ?: 0f,

        grade = try {
            if (this.grade != null) GradeEnum.valueOf(this.grade) else GradeEnum.E
        } catch (e: IllegalArgumentException) {
            GradeEnum.UNKNOWN
        },

        videoSuggestion = this.videoSuggestion,
        audioSuggestion = this.audioSuggestion ?: "No audio feedback available yet."
    )
}

