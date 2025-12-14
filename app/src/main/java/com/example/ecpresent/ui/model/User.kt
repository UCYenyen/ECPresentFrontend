package com.example.ecpresent.ui.model

data class User(
    val id: String,
    val username: String,
    val email: String,
    val password: String,
    val role: UserRole,
    val imageUrl: String,
    val avatar: Avatar,
    val createdAt: String,
    val updatedAt: String
)

enum class UserRole {
    ADMIN,
    USER,
    GUEST
}