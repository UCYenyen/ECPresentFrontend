package com.example.ecpresent.data.dto
import com.example.ecpresent.ui.model.Avatar
import com.google.gson.annotations.SerializedName
data class UserResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("username")
    val username: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("token")
    val token: String,
    @SerializedName("avatar")
    val avatar: Avatar,
    @SerializedName("image_url")
    val imageUrl: String?,
    @SerializedName("createdAt")
    val createdAt: String?,
    @SerializedName("updatedAt")
    val updatedAt: String?,
    @SerializedName("UserRole")
    val userRole: String?
)