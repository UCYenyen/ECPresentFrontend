package com.example.ecpresent.data.container


import com.example.ecpresent.data.Env
import com.example.ecpresent.data.service.AuthService
import com.example.ecpresent.data.repository.AuthRepository
import com.example.ecpresent.data.repository.LearningRepository
import com.example.ecpresent.data.service.LearningService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerContainer {
    companion object {
        const val BASE_URL = Env.BASE_URL
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitAuthService: AuthService by lazy {
        retrofit.create(AuthService::class.java)
    }

    val serverAuthRepository: AuthRepository by lazy {
        AuthRepository(retrofitAuthService)
    }

    private val retrofitLearningService: LearningService by lazy {
        retrofit.create(LearningService::class.java)
    }

    val serverLearningRepository: LearningRepository by lazy {
        LearningRepository(retrofitLearningService)
    }

}