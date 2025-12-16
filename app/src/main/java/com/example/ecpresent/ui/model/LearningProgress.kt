package com.example.ecpresent.ui.model

import com.example.ecpresent.data.dto.LearningProgressResponse
import com.example.ecpresent.data.dto.LearningResponseItem

data class LearningProgress(
    val id: String,
    val learning: Learning,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)

fun LearningProgressResponse.toLearningProgress(): LearningProgress {
    return LearningProgress(
        id = this.id,
        learning = this.learning.toLearning(),
        status = this.status,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}