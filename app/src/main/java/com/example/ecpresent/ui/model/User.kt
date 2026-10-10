package com.example.ecpresent.ui.model

import com.example.ecpresent.data.Env.BASE_URL
import com.example.ecpresent.data.dto.UserResponse
import com.example.ecpresent.enum.UserRole

data class User(
    val id: String,
    val username: String,
    val email: String,
    val token: String,
    val password: String,
    val role: UserRole?,
    val imageUrl: String?,
    val avatar: Avatar?,
    val createdAt: String,
    val updatedAt: String
)

fun UserResponse.toUser(): User{
    return User(
        id = this.id.toString(),
        username = this.username.toString(),
        password = this.password.toString(),
        email = this.email.toString(),
        token = this.token,
        role = this.userRole,
        imageUrl = "",
        avatar = this.avatar?.toAvatar() ?: Avatar(id = "", imageUrl = "${BASE_URL}uploads/avatar_2.jpg", createdAt = "", updatedAt = ""),
        createdAt = "",
        updatedAt = ""
    )
}