package com.example.ecpresent.utils // Sesuaikan package Anda

import com.example.ecpresent.enum.GradeEnum
import com.example.ecpresent.data.dto.AverageIndicatorResponse

object GradeHelper {

    // 1. Fungsi Menghitung Grade dari Rata-rata 3 Nilai
    fun calculateGrade(data: AverageIndicatorResponse): GradeEnum {
        // Hitung rata-rata dari 3 komponen
        val average = (data.averageIntonation + data.averageExpression + data.averagePosture) / 3

        return when {
            average >= 90 -> GradeEnum.S
            average >= 80 -> GradeEnum.A
            average >= 70 -> GradeEnum.B
            average >= 60 -> GradeEnum.C
            average >= 50 -> GradeEnum.D
            else -> GradeEnum.E
        }
    }

    // 2. Fungsi Mendapatkan Pesan berdasarkan Grade
    fun getGradeMessage(grade: GradeEnum): String {
        return when (grade) {
            GradeEnum.S -> "Perfect!"
            GradeEnum.A -> "Great Job!"     // <-- Pesan untuk A
            GradeEnum.B -> "Good Work"
            GradeEnum.C -> "Keep Trying"
            GradeEnum.D -> "Needs Practice"
            GradeEnum.E -> "Don't Give Up"
            GradeEnum.UNKNOWN -> "-"
        }
    }
}