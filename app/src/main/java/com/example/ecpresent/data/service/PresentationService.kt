package com.example.ecpresent.data.service

import com.example.ecpresent.data.dto.Answer
import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.dto.PresentationFeedbackResponse
import com.example.ecpresent.data.dto.PresentationAnalysisResponse
import com.example.ecpresent.data.dto.PresentationListResponse
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

    @Multipart
    @POST("presentations/{id}/answer")
    suspend fun submitAnswer(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Part audio: MultipartBody.Part,
    ): Response<BaseResponse<Answer>>

    @GET("presentations/{id}/feedback")
    suspend fun getFinalFeedback(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): Response<BaseResponse<PresentationFeedbackResponse>>

    @FormUrlEncoded
    @PATCH("presentations/{id}")
    suspend fun updateNotes(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Field("notes") notes: String
    ): Response<BaseResponse<PresentationFeedbackResponse>>

    @DELETE("presentations/{id}")
    suspend fun deletePresentation(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): Response<BaseResponse<Unit>>
}

