package com.example.ecpresent.ui.model

import com.example.ecpresent.data.dto.LearningProgressResponse

data class LearningProgress(
    val id: Int,
    val learning: Learning,
    val status: String,
    val createdAt: String,
    val updatedAt: String
)
fun LearningProgressResponse.toLearningProgress(): LearningProgress {
    return LearningProgress(
        id = this.id,
       status = this.status,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt,
    )
}
//enum class LearningStatus{
//    LOCKED,
//    ONPROGRESS,
//    COMPLETED
//}