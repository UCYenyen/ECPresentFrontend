package com.example.ecpresent.ui.model

import com.example.ecpresent.data.dto.Question as QuestionDto
data class Question(
    val id: Int,
    val presentationId: Int,
    val text: String,
    val timeLimit: Int
)

fun QuestionDto.toUiModel(): Question {
    return Question(
        id = this.id,
        presentationId = this.presentationId,
        text = this.question,
        timeLimit = this.timeLimitSeconds
    )
}


