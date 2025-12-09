package com.example.ecpresent

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.math.log10
import kotlin.math.sqrt

data class AudioData(
    val volumeDb: Double,
    val isMonotone: Boolean,
    val feedback: String
)

class AudioAnalyzer {
    @SuppressLint("MissingPermission")
    fun startListening(): Flow<AudioData> = flow {
        val sampleRate = 44100
        val minSize = AudioRecord.getMinBufferSize(
            sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT
        )

        if (minSize < 0) {
            emit(AudioData(0.0, true, "Error Mic"))
            return@flow
        }

        val audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC, sampleRate,
            AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, minSize
        )

        audioRecord.startRecording()
        val buffer = ShortArray(minSize)
        val pitchHistory = ArrayList<Float>()

        while (true) {
            val readSize = audioRecord.read(buffer, 0, minSize)
            if (readSize > 0) {
                var sum = 0.0
                for (i in 0 until readSize) {
                    sum += buffer[i] * buffer[i]
                }
                val rms = sqrt(sum / readSize)
                val db = if (rms > 0) 20 * log10(rms) else 0.0

                var zeroCrossings = 0
                for (i in 0 until readSize - 1) {
                    if ((buffer[i] > 0 && buffer[i+1] <= 0) ||
                        (buffer[i] <= 0 && buffer[i+1] > 0)) {
                        zeroCrossings++
                    }
                }
                val currentPitch = zeroCrossings.toFloat()

                pitchHistory.add(currentPitch)
                if (pitchHistory.size > 20) pitchHistory.removeAt(0)

                val stdDev = calculateStdDev(pitchHistory)
                val isMonotone = stdDev < 30.0 && db > 40

                val statusText = when {
                    db < 40 -> "🔈 Terlalu Pelan"
                    isMonotone -> "🤖 Nada Monoton"
                    else -> "✅ Intonasi Bagus"
                }

                emit(AudioData(db, isMonotone, statusText))
            }
            delay(100)
        }
    }

    private fun calculateStdDev(list: List<Float>): Float {
        if (list.size < 2) return 0f
        val mean = list.average().toFloat()
        var sumSquaredDiff = 0f
        for (num in list) {
            sumSquaredDiff += (num - mean) * (num - mean)
        }
        return sqrt(sumSquaredDiff / list.size)
    }
}