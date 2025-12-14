package com.example.ecpresent.data.container


import com.example.ecpresent.data.service.ServerService
import com.example.ecpresent.data.repository.ServerRepository
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerContainer {
    companion object {
        const val BASE_URL = "http://10.0.2.2:3000/api/"
    }

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
        .baseUrl(BASE_URL)
        .build()

    private val retrofitService: ServerService by lazy {
        retrofit.create(ServerService::class.java)
    }

    val serverRepository: ServerRepository by lazy {
        ServerRepository(retrofitService)
    }

}