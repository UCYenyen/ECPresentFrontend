package com.example.ecpresent.ui.model

data class User(
    val id: String = "s",
    val username: String = "",
    val email: String = "",
    val token: String = "",
    val password: String = "",
    val role: String,
    val imageUrl: String = "",
    val avatar: Avatar?,
    val createdAt: String?,
    val updatedAt: String?
)