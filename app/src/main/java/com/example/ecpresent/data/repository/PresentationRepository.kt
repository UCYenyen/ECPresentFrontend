package com.example.ecpresent.data.repository

import android.content.Context
import android.net.Uri
import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.dto.PresentationAnalysisResponse
import com.example.ecpresent.data.dto.PresentationFeedbackResponse
import com.example.ecpresent.data.dto.PresentationListResponse
import com.example.ecpresent.data.service.PresentationService
import com.example.ecpresent.ui.model.Answer
import com.example.ecpresent.ui.model.Presentation
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class PresentationRepository(private val service: PresentationService) {
    suspend fun uploadPresentation(token: String, fileUri: Uri, context: Context, title: String): Response<BaseResponse<Presentation>> {
        val formattedToken = "Bearer $token"
        val file = getFileFromUri(context, fileUri, "temp_video.mp4") ?: throw Exception("Gagal memproses file video")

        val requestFile = file.asRequestBody("video/*".toMediaTypeOrNull())
        val videoPart = MultipartBody.Part.createFormData("video", file.name, requestFile)
        val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())

        return service.createPresentation(formattedToken, videoPart, titlePart)
    }

    suspend fun getAnalysis(token: String, id: String): Response<BaseResponse<PresentationAnalysisResponse>>{
        val formattedToken = "Bearer $token"
        return service.getPresentationAnalysis(formattedToken, id)
    }
    suspend fun submitAnswer(token: String, presentationId: String, audioFile: File): Response<BaseResponse<Answer>> {
        val formattedToken = "Bearer $token"
        val requestFile = audioFile.asRequestBody("audio/mp4".toMediaTypeOrNull())
        val audioPart = MultipartBody.Part.createFormData("audio", audioFile.name, requestFile)

        return service.submitAnswer(formattedToken, presentationId, audioPart)
    }

    suspend fun getFinalFeedback(token: String, presentationId: String): Response<BaseResponse<PresentationFeedbackResponse>> {
        return service.getFinalFeedback("Bearer $token", presentationId)
    }

    suspend fun updateNotes(token: String, presentationId: Int, notes: String): Response<BaseResponse<PresentationFeedbackResponse>> {
        return service.updateNotes("Bearer $token", presentationId.toString(), notes)
    }

    suspend fun deletePresentation(token: String, presentationId: Int): Response<BaseResponse<Unit>> {
        return service.deletePresentation("Bearer $token", presentationId.toString())
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