package com.example.ecpresent.ui.model

import com.example.ecpresent.data.dto.LearningResponseItem

data class Learning(
    val id: String,
    val title: String,
    val description: String,
    val videoUrl: String,
    val createdAt: String,
    val updatedAt: String,
)

fun LearningResponseItem.toLearning(): Learning {
    return Learning(
        id = this.id.toString(),
        title = this.title,
        description = this.description,
        videoUrl = this.video_url,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}