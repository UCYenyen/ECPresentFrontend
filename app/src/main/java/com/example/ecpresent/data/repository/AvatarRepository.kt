package com.example.ecpresent.data.repository
import com.example.ecpresent.data.dto.AvatarResponse
import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.service.AvatarService
import okhttp3.MultipartBody
import retrofit2.Response
class AvatarRepository(private val service: AvatarService) {
    suspend fun getAvatars(token: String, id: Int): Response<BaseResponse<AvatarResponse>> {
        val formattedToken = "Bearer $token"
        return service.getAvatarById(token = formattedToken, id = id)
    }
    suspend fun getAllAvatars(token: String): Response<BaseResponse<List<AvatarResponse>>> {
        val formattedToken = "Bearer $token"
        return service.getAllAvatars(token = formattedToken)
    }
    suspend fun uploadCustomAvatar(token: String, file: MultipartBody.Part): Response<BaseResponse<AvatarResponse>> {
        val formattedToken = "Bearer $token"
        return service.uploadCustomAvatar(token = formattedToken, file = file)
    }
    suspend fun deleteAvatar(token: String, id: Int): Response<BaseResponse<AvatarResponse>> {
        val formattedToken = "Bearer $token"
        return service.deleteAvatar(token = formattedToken, id = id)
    }
}