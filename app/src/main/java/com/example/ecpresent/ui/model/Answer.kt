package com.example.ecpresent.ui.model
import com.example.ecpresent.data.dto.Answer as AnswerDto
data class Answer  (
    val id: Int,
    val score: Int,
    val suggestion: String,
    val audioUrl: String,

)

fun AnswerDto.toUiModel(): Answer {
    return Answer(
        id = this.id,
        score = this.score?.toInt() ?: 0,
        suggestion = this.suggestion ?: "No suggestion available.",
        audioUrl = this.audioUrl ?: ""
    )
}