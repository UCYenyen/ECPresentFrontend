package com.example.ecpresent.data.service

import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.dto.PresentationAnalysisResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface PresentationService {
    @GET("presentations/{id}/analysis")
    suspend fun getPresentationAnalysis(@Path("id") id: String): Response<BaseResponse<PresentationAnalysisResponse>>

    @Multipart
    @POST("presentations")
    suspend fun createPresentation(
        @Header("Authorization") token: String,
        @Part video: MultipartBody.Part,
        @Part("title") title: RequestBody
    ): Response<BaseResponse<PresentationAnalysisResponse>>
}