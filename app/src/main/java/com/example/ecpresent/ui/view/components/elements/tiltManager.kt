package com.example.ecpresent

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlin.math.abs

data class TiltResult(
    val isLevel: Boolean,
    val message: String,
    val tiltX: Float,
    val tiltZ: Float
)

class TiltManager(context: Context) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    fun startListening(): Flow<TiltResult> = callbackFlow {
        if (accelerometer == null) {
            trySend(TiltResult(true, "Sensor NA", 0f, 0f))
            close()
            return@callbackFlow
        }

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                event?.let {
                    val x = it.values[0]
                    val y = it.values[1]
                    val z = it.values[2]

                    val isUpright = y > 7.0
                    val isNotTiltedSideways = abs(x) < 2.0
                    val isNotFlat = abs(z) < 5.0

                    val status = when {
                        !isUpright -> "⚠️ Tegakkan HP!"
                        !isNotTiltedSideways -> "⚠️ HP Miring!"
                        !isNotFlat -> "⚠️ Jangan Tidurkan HP!"
                        else -> "✅ HP Pas"
                    }

                    val isLevel = isUpright && isNotTiltedSideways && isNotFlat
                    trySend(TiltResult(isLevel, status, x, z))
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        sensorManager.registerListener(listener, accelerometer, SensorManager.SENSOR_DELAY_UI)

        awaitClose {
            sensorManager.unregisterListener(listener)
        }
    }
}