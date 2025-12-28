package com.example.ecpresent.ui.model
import com.example.ecpresent.data.Env.ROOT_URL
import com.example.ecpresent.data.dto.AvatarResponse

data class Avatar(
    val id: String,
    val imageUrl: String,
    val createdAt: String,
    val updatedAt: String,
)

fun AvatarResponse.toAvatar(): Avatar{
    val rawUrl = this.imageUrl.ifBlank {
        "uploads/avatar_2.jpg"
    }
    val finalUrl = "$ROOT_URL$rawUrl"
    return Avatar(
        id = this.id.toString(),
        imageUrl = finalUrl,
        createdAt = this.createdAt,
        updatedAt = this.updatedAt
    )
}


