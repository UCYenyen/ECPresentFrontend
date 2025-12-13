package com.example.ecpresent.data.dto

data class UpdateUserRequest(
    val avatar_id: Int,
    val email: String,
    val password: String,
    val username: String
)