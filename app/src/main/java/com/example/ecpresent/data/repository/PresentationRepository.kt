package com.example.ecpresent.data.repository

import android.content.Context
import android.net.Uri
import com.example.ecpresent.data.dto.Answer
import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.dto.PresentationAnalysisResponse
import com.example.ecpresent.data.service.PresentationService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class PresentationRepository(private val service: PresentationService) {

    suspend fun uploadPresentation(token: String, fileUri: Uri, context: Context, title: String): Response<BaseResponse<PresentationAnalysisResponse>> {
        val formattedToken = "Bearer $token"

        val file = getFileFromUri(context, fileUri, "temp_video.mp4") ?: throw Exception("Gagal memproses file video")

        // Backend mengharapkan 'video' sesuai 'uploadVideo.single('video')'
        val requestFile = file.asRequestBody("video/*".toMediaTypeOrNull())
        val videoPart = MultipartBody.Part.createFormData("video", file.name, requestFile)

        // Backend membaca title dari req.body
        val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())

        return service.createPresentation(formattedToken, videoPart, titlePart)
    }



    suspend fun submitAnswer(token: String, presentationId: String, audioUri: Uri, context: Context): Response<BaseResponse<Answer>> {
        val formattedToken = "Bearer $token"

        val file = getFileFromUri(context, audioUri, "temp_answer.mp3") ?: throw Exception("Gagal memproses file audio")

        val requestFile = file.asRequestBody("audio/*".toMediaTypeOrNull())
        val audioPart = MultipartBody.Part.createFormData("audio", file.name, requestFile)

        return service.submitAnswer(formattedToken, presentationId, audioPart)
    }


    private fun getFileFromUri(context: Context, uri: Uri, defaultFileName: String): File? {
        return try {
            val contentResolver = context.contentResolver
            val fileName = "${System.currentTimeMillis()}_$defaultFileName"
            val tempFile = File(context.cacheDir, fileName)

            contentResolver.openInputStream(uri)?.use { inputStream ->
                FileOutputStream(tempFile).use { outputStream ->
                    inputStream.copyTo(outputStream)
                }
            }
            tempFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


}