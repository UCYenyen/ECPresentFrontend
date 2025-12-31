package com.example.ecpresent.data.service

import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.dto.LearningProgressResponse
import com.example.ecpresent.data.dto.LearningResponseItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface LearningService {
    @GET("api/getLearning/{id}")
    suspend fun getLearning(@Path("id") id: String): Response<LearningResponseItem>

    @GET("api/getAllLearnings")
    suspend fun getAllLearnings(): Response<BaseResponse<List<LearningResponseItem>>>

    @GET("api/myLearningProgresses")
    suspend fun getMyLearningProgresses(
        @Header("Authorization") token: String
    ): Response<BaseResponse<List<LearningProgressResponse>>>

    @GET("api/myLearningProgress/{id}")
    suspend fun getMyLearningProgress(@Path("id") id: String): Response<BaseResponse<LearningProgressResponse>>

    @POST("api/startLearning/{id}")
    suspend fun startLearning(@Header("Authorization") token: String, @Path("id") id: String): Response<BaseResponse<LearningProgressResponse>>

    @PUT("api/completeLearning/{id}")
    suspend fun completeLearning(@Header("Authorization") token: String, @Path("id") id: String): Response<BaseResponse<LearningProgressResponse>>
}