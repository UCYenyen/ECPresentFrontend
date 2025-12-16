package com.example.ecpresent.ui.model

data class Presentation(
    val id: Int,
    val user: User,
    val videoUrl: String,
    val title: String,
    val status: PresentationStatus,
    val personalNotes: String? = null,
    val createdAt: String,
    val updatedAt: String,
    val questions: List<Question>
)

enum class PresentationStatus {
    ONGOING,
    COMPLETED,
    FAILED
}