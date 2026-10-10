package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class ProcessingInfo(
    val status: String,
    val stage: String?,
    @SerializedName("upload_attempts")
    val uploadAttempts: Int,
    @SerializedName("generate_attempts")
    val generateAttempts: Int,
    @SerializedName("pipeline_mode")
    val pipelineMode: String
)
