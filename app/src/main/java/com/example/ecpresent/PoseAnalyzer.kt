package com.example.ecpresent

import android.content.Context
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
import java.lang.Math.abs
import java.lang.Math.atan2

data class PoseResult(
    val statusText: String,
    val isReadyToStart: Boolean,
    val isPostureGood: Boolean
)

class PoseAnalyzer(
    val context: Context,
    val onResult: (PoseResult) -> Unit
) : ImageAnalysis.Analyzer {

    private var poseLandmarker: PoseLandmarker? = null

    init {
        val baseOptions = BaseOptions.builder()
            .setModelAssetPath("pose_landmarker_full.task")
            .build()

        val options = PoseLandmarker.PoseLandmarkerOptions.builder()
            .setBaseOptions(baseOptions)
            .setRunningMode(RunningMode.LIVE_STREAM)
            .setResultListener { result, _ ->
                val landmarks = result.landmarks()
                if (landmarks.isNotEmpty() && landmarks[0].isNotEmpty()) {
                    val points = landmarks[0]

                    val nose = points[0]
                    val leftAnkle = points[27]
                    val rightAnkle = points[28]
                    val leftShoulder = points[11]
                    val rightShoulder = points[12]

                    val isHeadVisible = nose.y() > 0.01
                    val isFeetVisible = leftAnkle.y() < 0.99 && rightAnkle.y() < 0.99

                    val bodyHeight = abs((leftAnkle.y() + rightAnkle.y())/2 - nose.y())
                    val isFarEnough = bodyHeight < 0.6

                    val aspectRatioCompensation = 1.77

                    val deltaY = (rightShoulder.y() - leftShoulder.y()) * aspectRatioCompensation
                    val deltaX = rightShoulder.x() - leftShoulder.x()

                    val angleDegrees = Math.toDegrees(atan2(deltaY, deltaX.toDouble()))

                    val isPostureGood = abs(angleDegrees) < 25.0

                    val (text, ready) = when {
                        !isHeadVisible -> "⚠️ Kepala Terpotong" to false
                        !isFeetVisible -> "⚠️ Kaki Terpotong" to false
                        !isFarEnough -> "⚠️ Mundur ke 5 Meter" to false
                        !isPostureGood -> "⚠️ Bahu Miring (${abs(angleDegrees).toInt()}°)" to true
                        else -> "✅ Posisi Tegak & Jarak Pas" to true
                    }

                    onResult(PoseResult(text, ready, isPostureGood))
                } else {
                    onResult(PoseResult("🔍 Mencari Tubuh...", false, false))
                }
            }
            .build()

        poseLandmarker = PoseLandmarker.createFromOptions(context, options)
    }

    override fun analyze(imageProxy: ImageProxy) {
        if (poseLandmarker == null) {
            imageProxy.close()
            return
        }

        val bitmap = imageProxy.toBitmap()
        val mpImage = BitmapImageBuilder(bitmap).build()
        poseLandmarker?.detectAsync(mpImage, System.currentTimeMillis())
        imageProxy.close()
    }
}