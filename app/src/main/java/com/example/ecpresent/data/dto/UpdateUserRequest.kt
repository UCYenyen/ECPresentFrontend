package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class UpdateUserRequest(
    @SerializedName("avatar_id")
    val avatarId: Int,

    @SerializedName("email")
    val email: String,     // Wajib diisi

    @SerializedName("password")
    val password: String,  // Wajib diisi (Minimal 8 huruf)

    @SerializedName("username")
    val username: String   // Wajib diisi
)