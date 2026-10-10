package com.example.ecpresent.data.repository

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.example.ecpresent.data.dto.Answer
import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.dto.ClientMetricsRequest
import com.example.ecpresent.data.dto.PresentationAnalysisResponse
import com.example.ecpresent.data.dto.PresentationFeedbackResponse
import com.example.ecpresent.data.dto.PresentationListResponse
import com.example.ecpresent.data.dto.PresentationResponse
import com.example.ecpresent.data.service.PresentationService
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class PresentationRepository(private val service: PresentationService) {

    // Menyalin video dari Uri ke cache supaya bisa dikirim sebagai multipart
    fun prepareVideoFile(context: Context, fileUri: Uri): File {
        return getFileFromUri(context, fileUri, "temp_video.mp4") ?: throw Exception("Gagal memproses file video")
    }

    fun getVideoDurationMs(context: Context, fileUri: Uri): Long? {
        val retriever = MediaMetadataRetriever()
        return try {
            retriever.setDataSource(context, fileUri)
            retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLongOrNull()
        } catch (e: Exception) {
            null
        } finally {
            retriever.release()
        }
    }

    suspend fun uploadPresentation(token: String, file: File, title: String, videoDurationMs: Long?): Response<BaseResponse<PresentationResponse>> {
        val formattedToken = "Bearer $token"

        // Backend mengharapkan 'video' sesuai 'uploadVideo.single('video')'
        val requestFile = file.asRequestBody("video/*".toMediaTypeOrNull())
        val videoPart = MultipartBody.Part.createFormData("video", file.name, requestFile)

        // Backend membaca title dari req.body
        val titlePart = title.toRequestBody("text/plain".toMediaTypeOrNull())
        val durationPart = videoDurationMs?.toString()?.toRequestBody("text/plain".toMediaTypeOrNull())

        return service.createPresentation(formattedToken, videoPart, titlePart, durationPart)
    }

    suspend fun getAnalysis(token: String, presentationId: Int): Response<BaseResponse<PresentationAnalysisResponse>> {
        return service.getPresentationAnalysis("Bearer $token", presentationId)
    }

    suspend fun sendClientMetrics(token: String, presentationId: Int, metrics: ClientMetricsRequest): Response<Unit> {
        return service.sendClientMetrics("Bearer $token", presentationId, metrics)
    }



    suspend fun submitAnswer(token: String, presentationId: String, audioFile: File): Response<BaseResponse<Answer>> {
        val formattedToken = "Bearer $token"
        // Gunakan audio/mp4 atau audio/m4a sesuai format MediaRecorder
        val requestFile = audioFile.asRequestBody("audio/mp4".toMediaTypeOrNull())
        val audioPart = MultipartBody.Part.createFormData("audio", audioFile.name, requestFile)

        return service.submitAnswer(formattedToken, presentationId, audioPart)
    }

    suspend fun getFinalFeedback(token: String, presentationId: String): Response<BaseResponse<PresentationFeedbackResponse>> {
        return service.getFinalFeedback("Bearer $token", presentationId)
    }

    suspend fun updateNotes(token: String, presentationId: Int, notes: String): Response<BaseResponse<PresentationFeedbackResponse>> {
        return service.updateNotes("Bearer $token", presentationId, notes)
    }

    suspend fun deletePresentation(token: String, presentationId: Int): Response<BaseResponse<Unit>> {
        return service.deletePresentation("Bearer $token", presentationId)
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