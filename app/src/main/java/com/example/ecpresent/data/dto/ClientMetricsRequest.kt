package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

// Waktu yang diukur di aplikasi, dikirim ke backend untuk data eksperimen latensi
data class ClientMetricsRequest(
    @SerializedName("client_platform")
    val clientPlatform: String = "android",
    @SerializedName("client_prepare_ms")
    val clientPrepareMs: Long,
    @SerializedName("client_request_ms")
    val clientRequestMs: Long,
    @SerializedName("client_wait_ms")
    val clientWaitMs: Long,
    @SerializedName("client_total_ms")
    val clientTotalMs: Long,
    @SerializedName("client_poll_count")
    val clientPollCount: Int,
    @SerializedName("client_outcome")
    val clientOutcome: String
)
