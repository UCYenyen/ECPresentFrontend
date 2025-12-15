package com.example.ecpresent.data.service

import com.example.ecpresent.data.dto.LearningResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface LearningService {
    @GET("get-my-learnings")
    suspend fun getLearningData(): Response<List<LearningResponseItem>>
}