package com.example.ecpresent.data.repository

import com.example.ecpresent.data.dto.BaseResponse
import com.example.ecpresent.data.dto.LearningProgressResponse
import com.example.ecpresent.data.dto.LearningResponseItem
import com.example.ecpresent.data.service.LearningService
import retrofit2.Response

class LearningRepository(private val service: LearningService) {
    suspend fun getAllLearnings(): Response<BaseResponse<List<LearningResponseItem>>> {
        return service.getAllLearnings()
    }
    suspend fun getLearningById(id: String): Response<LearningResponseItem> {
        return service.getLearning(id)
    }

    suspend fun getMyLearningProgresses(token: String): Response<BaseResponse<List<LearningProgressResponse>>> {
        val formattedToken = "Bearer $token"
        return service.getMyLearningProgresses(token = formattedToken)
    }

    suspend fun getMyLearningProgress(token: String): Response<LearningProgressResponse> {
        val formattedToken = "Bearer $token"
        return service.getMyLearningProgress(formattedToken)
    }

    suspend fun startLearning(token: String, learningId: String): Response<LearningProgressResponse> {
        val formattedToken = "Bearer $token"
        return service.startLearning(formattedToken, learningId)
    }

    suspend fun completeLearning(token: String, learningProgressID: String): Response<LearningProgressResponse> {
        val formattedToken = "Bearer $token"
        return service.completeLearning(formattedToken, learningProgressID)
    }
}