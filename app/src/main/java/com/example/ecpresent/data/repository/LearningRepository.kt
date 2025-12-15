package com.example.ecpresent.data.repository

import com.example.ecpresent.data.dto.LearningResponseItem
import com.example.ecpresent.data.service.LearningService
import retrofit2.Response

class LearningRepository(private val service: LearningService) {
    suspend fun getAllLearnings(): Response<List<LearningResponseItem>> {
        return service.getLearningData()
    }
}