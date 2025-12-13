package com.example.ecpresent.data.dto

data class RegisterUserRequest(
    val email: String,
    val password: String,
    val username: String
)