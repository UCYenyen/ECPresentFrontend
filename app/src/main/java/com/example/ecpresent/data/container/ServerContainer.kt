package com.example.ecpresent.data.container


import com.example.ecpresent.data.Env
import com.example.ecpresent.data.service.AuthService
import com.example.ecpresent.data.repository.AuthRepository
import com.example.ecpresent.data.repository.LearningRepository
import com.example.ecpresent.data.repository.PresentationRepository
import com.example.ecpresent.data.service.LearningService
import com.example.ecpresent.data.service.PresentationService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ServerContainer {
    companion object {
        const val BASE_URL = Env.BASE_URL
    }
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS) // Batas waktu nyambung
        .readTimeout(120, TimeUnit.SECONDS)   // Batas waktu nunggu respon server (PENTING BUAT AI)
        .writeTimeout(120, TimeUnit.SECONDS)  // Batas waktu upload file (PENTING BUAT VIDEO)
        .build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .client(okHttpClient)
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

    private val retrofitPresentationService: PresentationService by lazy {
        retrofit.create(PresentationService::class.java)
    }

    val serverPresentationRepository: PresentationRepository by lazy {
        PresentationRepository(retrofitPresentationService)
    }
}