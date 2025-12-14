package com.example.ecpresent.ui.model

data class LearningProgress(
    val id: Int,
    val learning: Learning,
    val status: LearningStatus,
    val createdAt: String,
    val updatedAt: String
)

enum class LearningStatus{
    LOCKED,
    ONPROGRESS,
    COMPLETED
}