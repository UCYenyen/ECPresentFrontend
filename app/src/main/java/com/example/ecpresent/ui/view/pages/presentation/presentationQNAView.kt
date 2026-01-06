import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.uistates.QnAUIState
import com.example.ecpresent.ui.viewmodel.PresentationViewModel
import java.io.File
import java.io.IOException

@Composable
fun PresentationQNAView(
    navController: NavController,
    presentationViewModel: PresentationViewModel = viewModel(),
    presentationId: String
) {
    val qnaState by presentationViewModel.qnaState.collectAsState()
    val timer by presentationViewModel.timer.collectAsState()
    val isRecording by presentationViewModel.isRecording.collectAsState()
    val context = LocalContext.current
    var mediaRecorder: MediaRecorder? by remember { mutableStateOf(null) }
    val audioFile = remember { File(context.cacheDir, "temp_answer.mp4") }
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Microphone permission needed", Toast.LENGTH_SHORT).show()
        }
    }

    // Helper: Stop & Submit
    fun stopAndSubmit() {
        try {
            mediaRecorder?.stop()
            mediaRecorder?.release()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mediaRecorder = null
        presentationViewModel.submitAnswer(audioFile)
    }

    // Helper: Start Recording
    fun startRecording() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            return
        }

        try {
            mediaRecorder = MediaRecorder().apply {
                setAudioSource(MediaRecorder.AudioSource.MIC)
                setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                setOutputFile(audioFile.absolutePath)
                prepare()
                start()
            }
            presentationViewModel.setRecordingState(true)
            presentationViewModel.startTimer { stopAndSubmit() }
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(context, "Failed to start recording", Toast.LENGTH_SHORT).show()
        }
    }

    when(val state = qnaState){
        is QnAUIState.Initial -> {
            LaunchedEffect(Unit) {
                presentationViewModel.getAnalysis(presentationId)
            }
        }

        is QnAUIState.AnswerScored -> {
            val answerData = state.answer
            AlertDialog(
                onDismissRequest = { },
                title = { Text("Audio Analysis Result") },
                text = {
                    Column {
                        Text(
                            text = "Audio Score: ${String.format("%.1f", answerData.score ?: 0.0)}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Suggestion:", fontWeight = FontWeight.SemiBold)
                        Text(answerData.suggestion ?: "No suggestion provided.")
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            navController.navigate(AppView.PresentationFeedback.name) {
                                popUpTo(AppView.PresentationFeedback.name) { inclusive = true }
                            }
                        }
                    ) {
                        Text("Continue to Final Result")
                    }
                }
            )
        }
        is QnAUIState.Error -> {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = state.msg,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        is QnAUIState.Success-> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.surface)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "QnA Session",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(24.dp))
                Card(
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = state.data.question?.question ?: "No Question Generated",
                        modifier = Modifier.padding(24.dp),
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        lineHeight = 28.sp
                    )
                }
                Spacer(modifier = Modifier.height(48.dp))
            }
        }
        is QnAUIState.Loading -> {
            CircularProgressIndicator()
            Spacer(modifier = Modifier.height(16.dp))
            Text("Analyzing Answer...", color = MaterialTheme.colorScheme.onSurface)
        }
        is QnAUIState.OnUserSubmitAnswer -> {
            Text(
                text = "00:${timer.toString().padStart(2, '0')}",
                fontSize = 64.sp,
                fontWeight = FontWeight.Bold,
                color = if (timer <= 3) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "Seconds Remaining",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (!isRecording) {
                Button(
                    onClick = { startRecording() },
                    modifier = Modifier.height(56.dp).fillMaxWidth(0.7f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3478E4))
                ) {
                    Text("Start Answering (Record)", fontWeight = FontWeight.Bold)
                }
            } else {
                Button(
                    onClick = { stopAndSubmit() },
                    modifier = Modifier.height(56.dp).fillMaxWidth(0.7f),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Stop & Submit", fontWeight = FontWeight.Bold)
                }
            }
        }
        else -> { }
    }
}

//@Composable
//@Preview(showBackground = true)
//private fun PresentationQNAViewPreview() {
//    PresentationQNAView(navController = rememberNavController(), )
//}