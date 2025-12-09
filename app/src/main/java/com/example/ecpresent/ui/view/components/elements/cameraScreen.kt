package com.example.ecpresent.ui.view.components.elements

import com.example.ecpresent.FaceAnalyzer
import com.example.ecpresent.PoseAnalyzer
import com.example.ecpresent.PoseResult
import com.example.ecpresent.AudioAnalyzer
import com.example.ecpresent.TiltManager
import com.example.ecpresent.TiltResult

import android.util.Log
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import java.util.concurrent.Executors

@Composable
fun CameraScreen(onNavigateBack: () -> Unit) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var isRecording by remember { mutableStateOf(false) }
    var showReportDialog by remember { mutableStateOf(false) }
    var showShortTimeWarning by remember { mutableStateOf(false) }

    var expressionStatus by remember { mutableStateOf("😐 ...") }
    var poseResult by remember { mutableStateOf(PoseResult("Mencari...", false, false)) }
    var tiltResult by remember { mutableStateOf(TiltResult(false, "Cek HP...", 0f, 0f)) }

    var voiceStatusText by remember { mutableStateOf("🎤 ...") }
    var isVoiceMonotone by remember { mutableStateOf(false) }

    var timerSeconds by remember { mutableLongStateOf(0L) }

    var totalFaceFrames by remember { mutableLongStateOf(0L) }
    var smileFrames by remember { mutableLongStateOf(0L) }
    var totalPoseFrames by remember { mutableLongStateOf(0L) }
    var goodPostureFrames by remember { mutableLongStateOf(0L) }
    var totalAudioSamples by remember { mutableLongStateOf(0L) }
    var dynamicVoiceSamples by remember { mutableLongStateOf(0L) }

    val isSystemReady = poseResult.isReadyToStart && tiltResult.isLevel

    val boxColor by animateColorAsState(
        targetValue = if (isSystemReady) Color.Green else Color.Red,
        label = "boxColor"
    )

    fun resetData() {
        totalFaceFrames = 0
        smileFrames = 0
        totalPoseFrames = 0
        goodPostureFrames = 0
        totalAudioSamples = 0
        dynamicVoiceSamples = 0
        timerSeconds = 0
    }

    LaunchedEffect(Unit) {
        TiltManager(context).startListening().collect { result ->
            tiltResult = result
        }
    }

    LaunchedEffect(isRecording) {
        if (isRecording) {
            val startTime = System.currentTimeMillis()
            while (isRecording) {
                val elapsed = (System.currentTimeMillis() - startTime) / 1000
                timerSeconds = elapsed
                delay(1000)
            }
        }
    }

    LaunchedEffect(isRecording) {
        if (isRecording) {
            try {
                AudioAnalyzer().startListening().collect { data ->
                    voiceStatusText = "${data.feedback} (${data.volumeDb.toInt()} dB)"
                    isVoiceMonotone = data.isMonotone
                    if (data.volumeDb > 40) {
                        totalAudioSamples++
                        if (!data.isMonotone) dynamicVoiceSamples++
                    }
                }
            } catch (e: Exception) {
                voiceStatusText = "Error Mic"
            }
        } else {
            voiceStatusText = "🎤 Mic Standby"
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    scaleType = PreviewView.ScaleType.FILL_CENTER
                }
            },
            modifier = Modifier.fillMaxSize(),
            update = { previewView ->
                val cameraProviderFuture = ProcessCameraProvider.getInstance(context)
                cameraProviderFuture.addListener({
                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build()
                    preview.setSurfaceProvider(previewView.surfaceProvider)

                    val faceAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    faceAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), FaceAnalyzer(context) { result ->
                        val isSmiling = result.smileScore > 0.4
                        expressionStatus = if (isSmiling) "😁 Senyum" else "😐 Datar"
                        if (isRecording) {
                            totalFaceFrames++
                            if (isSmiling) smileFrames++
                        }
                    })

                    val poseAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build()
                    poseAnalysis.setAnalyzer(Executors.newSingleThreadExecutor(), PoseAnalyzer(context) { result ->
                        poseResult = result
                        if (isRecording && result.isReadyToStart) {
                            totalPoseFrames++
                            if (result.isPostureGood) goodPostureFrames++
                        }
                    })

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_FRONT_CAMERA,
                            preview,
                            faceAnalysis,
                            poseAnalysis
                        )
                    } catch (e: Exception) {
                        Log.e("CameraScreen", "Bind Error", e)
                    }

                }, ContextCompat.getMainExecutor(context))
            }
        )

        Canvas(modifier = Modifier.fillMaxSize()) {
            val centerX = size.width / 2
            val centerY = size.height / 2

            val barWidth = size.width * 0.8f
            val sensitivity = 80f

            val indicatorColor = if (tiltResult.isLevel) Color.Green else Color.Red
            val baseLineColor = Color.White.copy(alpha = 0.6f)

            drawLine(
                color = baseLineColor,
                start = Offset(centerX - (barWidth / 2), centerY),
                end = Offset(centerX + (barWidth / 2), centerY),
                strokeWidth = 6f,
                cap = StrokeCap.Round
            )

            drawLine(
                color = baseLineColor,
                start = Offset(centerX, centerY - 20f),
                end = Offset(centerX, centerY + 20f),
                strokeWidth = 4f
            )
            drawLine(
                color = baseLineColor,
                start = Offset(centerX - 40f, centerY - 10f),
                end = Offset(centerX - 40f, centerY + 10f),
                strokeWidth = 2f
            )
            drawLine(
                color = baseLineColor,
                start = Offset(centerX + 40f, centerY - 10f),
                end = Offset(centerX + 40f, centerY + 10f),
                strokeWidth = 2f
            )

            val bubbleOffsetRaw = -(tiltResult.tiltX * sensitivity)
            val maxOffset = (barWidth / 2) - 20f
            val bubbleOffset = bubbleOffsetRaw.coerceIn(-maxOffset, maxOffset)

            drawCircle(
                color = indicatorColor,
                radius = 15f,
                center = Offset(centerX + bubbleOffset, centerY)
            )
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .border(4.dp, boxColor, RoundedCornerShape(16.dp))
        ) {
            // Tombol Back (Hanya muncul jika TIDAK sedang merekam)
            if (!isRecording) {
                IconButton(
                    onClick = onNavigateBack,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp)
                        .background(Color.Black.copy(alpha = 0.4f), CircleShape)
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.White
                    )
                }
            }

            if (!isSystemReady && !isRecording) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(120.dp))

                    val icon = if (!tiltResult.isLevel) Icons.Default.Phone else Icons.Default.Warning
                    Icon(icon, null, tint = Color.Red, modifier = Modifier.size(64.dp))

                    val mainWarning = if (!tiltResult.isLevel) "HP BELUM LURUS" else "POSISI SALAH"
                    val subWarning = if (!tiltResult.isLevel) tiltResult.message else poseResult.statusText

                    Text(mainWarning, color = Color.Red, fontSize = 24.sp, fontWeight = FontWeight.Black)
                    Text(subWarning, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }

            if (isRecording) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 60.dp)
                        .background(if (timerSeconds < 60) Color.Red else Color.Green, RoundedCornerShape(8.dp))
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    val minutes = timerSeconds / 60
                    val seconds = timerSeconds % 60
                    Text(
                        String.format("%02d:%02d", minutes, seconds),
                        color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        if (!isRecording && isSystemReady) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "Siap! HP Lurus & Jarak Pas",
                    color = Color.Green,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 120.dp)
                )
            }
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
        ) {
            val isButtonEnabled = isRecording || isSystemReady

            Button(
                onClick = {
                    if (isRecording) {
                        if (timerSeconds < 60) {
                            showShortTimeWarning = true
                        } else {
                            isRecording = false
                            showReportDialog = true
                        }
                    } else {
                        resetData()
                        isRecording = true
                    }
                },
                enabled = isButtonEnabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isRecording) Color.Red else if (isButtonEnabled) Color.Green else Color.Gray
                ),
                modifier = Modifier.size(width = 220.dp, height = 60.dp)
            ) {
                Icon(
                    if (isRecording) Icons.Default.CheckCircle else Icons.Filled.PlayArrow,
                    null, tint = Color.White
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    if (isRecording) "STOP" else if (isSystemReady) "MULAI" else "ATUR POSISI",
                    fontSize = 16.sp, fontWeight = FontWeight.Bold
                )
            }
        }

        if (isRecording) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(24.dp)
                    .padding(bottom = 80.dp)
                    .background(Color.Black.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                    .padding(16.dp)
            ) {
                Text("JARAK: ${poseResult.statusText}", color = boxColor, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text("EKSPRESI: $expressionStatus", color = Color.White, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                val audioColor = if (isVoiceMonotone) Color.Red else Color.Cyan
                Text("SUARA: $voiceStatusText", color = audioColor, fontWeight = FontWeight.Bold)
            }
        }

        if (showShortTimeWarning) {
            AlertDialog(
                onDismissRequest = { showShortTimeWarning = false },
                title = { Text("Waktu < 1 Menit") },
                text = { Text("Minimal presentasi 1 menit. Yakin berhenti?") },
                confirmButton = {
                    Button(
                        onClick = {
                            showShortTimeWarning = false
                            isRecording = false
                            showReportDialog = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                    ) { Text("Ya, Berhenti") }
                },
                dismissButton = {
                    Button(onClick = { showShortTimeWarning = false }) { Text("Lanjut") }
                }
            )
        }

        if (showReportDialog) {
            val smileScore = if (totalFaceFrames > 0) (smileFrames * 100 / totalFaceFrames) else 0
            val postureScore = if (totalPoseFrames > 0) (goodPostureFrames * 100 / totalPoseFrames) else 0
            val audioScore = if (totalAudioSamples > 0) (dynamicVoiceSamples * 100 / totalAudioSamples) else 0

            Dialog(onDismissRequest = { showReportDialog = false }) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.padding(16.dp).fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("📊 RAPOR", fontSize = 22.sp, fontWeight = FontWeight.Black, color = Color.Black)
                        Text("Durasi: ${timerSeconds}s", fontSize = 16.sp, color = Color.Gray)
                        Spacer(Modifier.height(24.dp))

                        ScoreItem("😁 Senyum", "$smileScore%", if (smileScore > 50) Color.Green else Color.Red)
                        ScoreItem("🧍 Postur", "$postureScore%", if (postureScore > 70) Color.Green else Color.Red)
                        ScoreItem("🔊 Intonasi", "$audioScore%", if (audioScore > 50) Color.Green else Color.Red)

                        Spacer(Modifier.height(24.dp))

                        val feedback = when {
                            timerSeconds < 60 -> "Waktu terlalu singkat."
                            smileScore < 30 -> "Kurang senyum."
                            postureScore < 50 -> "Postur goyang."
                            audioScore < 30 -> "Suara datar."
                            else -> "Mantap!"
                        }

                        Text(feedback, color = Color.DarkGray, fontWeight = FontWeight.Medium)
                        Spacer(Modifier.height(16.dp))

                        Button(onClick = { showReportDialog = false }) {
                            Icon(Icons.Default.Refresh, null)
                            Text("Tutup")
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ScoreItem(label: String, value: String, color: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text(value, fontSize = 20.sp, fontWeight = FontWeight.ExtraBold, color = color)
    }
}