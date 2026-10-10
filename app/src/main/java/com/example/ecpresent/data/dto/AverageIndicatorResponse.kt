package com.example.ecpresent.data.dto

import com.google.gson.annotations.SerializedName

data class AverageIndicatorResponse (
    @SerializedName("average_intonation")
    val averageIntonation: Float,
    @SerializedName("average_expression")
    val averageExpression: Float,
    @SerializedName("average_posture")
    val averagePosture: Float,
)