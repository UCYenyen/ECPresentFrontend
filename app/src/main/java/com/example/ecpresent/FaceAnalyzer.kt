package com.example.ecpresent

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.facelandmarker.FaceLandmarker

data class FaceResult(val smileScore: Float, val isEyeContact: Boolean)

class FaceAnalyzer(
    val context: Context,
    val onResult: (FaceResult) -> Unit
) : ImageAnalysis.Analyzer {

    private var faceLandmarker: FaceLandmarker? = null

    init {
        val baseOptions = BaseOptions.builder()
            .setModelAssetPath("face_landmarker.task")
            .build()

        val options = FaceLandmarker.FaceLandmarkerOptions.builder()
            .setBaseOptions(baseOptions)
            .setRunningMode(RunningMode.LIVE_STREAM)
            .setNumFaces(1)
            .setOutputFaceBlendshapes(true)
            .setResultListener { result, _ ->
                val faces = result.faceBlendshapes()
                if (faces.isPresent && faces.get().isNotEmpty()) {
                    val face = faces.get()[0]

                    val smile = (face.firstOrNull { it.categoryName() == "mouthSmileLeft" }?.score() ?: 0f) +
                            (face.firstOrNull { it.categoryName() == "mouthSmileRight" }?.score() ?: 0f) / 2

                    val lookLeft = face.firstOrNull { it.categoryName() == "eyeLookInLeft" }?.score() ?: 0f
                    val lookRight = face.firstOrNull { it.categoryName() == "eyeLookInRight" }?.score() ?: 0f

                    val isEyeContact = lookLeft < 0.2 && lookRight < 0.2

                    onResult(FaceResult(smile, isEyeContact))
                }
            }
            .build()

        faceLandmarker = FaceLandmarker.createFromOptions(context, options)
    }

    override fun analyze(imageProxy: ImageProxy) {
        if (faceLandmarker == null) {
            imageProxy.close()
            return
        }

        val matrix = Matrix().apply {
            postRotate(imageProxy.imageInfo.rotationDegrees.toFloat())
        }

        val bitmap = imageProxy.toBitmap()
        val rotatedBitmap = Bitmap.createBitmap(
            bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true
        )

        val mpImage = BitmapImageBuilder(rotatedBitmap).build()
        faceLandmarker?.detectAsync(mpImage, System.currentTimeMillis())

        imageProxy.close()
    }
}