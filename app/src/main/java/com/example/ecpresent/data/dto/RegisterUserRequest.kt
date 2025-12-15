package com.example.ecpresent.data.dto

data class RegisterUserRequest(
    val username: String,
    val email: String,
    val password: String
)