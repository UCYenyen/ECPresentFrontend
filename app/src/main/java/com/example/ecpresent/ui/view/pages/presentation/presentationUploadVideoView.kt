package com.example.ecpresent.ui.view.pages.presentation

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.uistates.UploadPresentationUIState
import com.example.ecpresent.ui.viewmodel.AuthViewModel
import com.example.ecpresent.ui.viewmodel.PresentationViewModel

@Composable
fun PresentationUploadVideoView(navController: NavController, presentationViewModel: PresentationViewModel = viewModel()) {
    var presentationTitle by remember { mutableStateOf("") }
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current

    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            videoUri = uri
        }
    )

    val uploadState by presentationViewModel.uploadPresentationUIState.collectAsState()
    val isBusy = uploadState is UploadPresentationUIState.Loading || uploadState is UploadPresentationUIState.Processing

    LaunchedEffect(uploadState) {
        when (uploadState) {
            is UploadPresentationUIState.Success -> {
                Toast.makeText(context, "Analysis ready!", Toast.LENGTH_SHORT).show()
                navController.navigate(AppView.FollowUpQuestion.name) {
                    popUpTo(AppView.FollowUpQuestion.name) { inclusive = true }
                }
                presentationViewModel.resetState()
            }
            else -> {}
        }
    }

    // Kegagalan analisis ditampilkan sebagai dialog supaya user tahu harus mengunggah ulang
    (uploadState as? UploadPresentationUIState.Error)?.let { state ->
        AlertDialog(
            onDismissRequest = { presentationViewModel.resetState() },
            title = { Text("Processing Failed") },
            text = { Text(state.message) },
            confirmButton = {
                TextButton(onClick = { presentationViewModel.resetState() }) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Presentation Title",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = presentationTitle,
                onValueChange = { presentationTitle = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Input your presentation title") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clickable { videoPickerLauncher.launch("video/*") }
                    .border(
                        width = 2.dp,
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
                ),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 16.dp, horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (videoUri == null) {
                        Icon(
                            imageVector = Icons.Outlined.CloudUpload,
                            contentDescription = "Upload Video",
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Tap To Upload Video",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Choose A Presentation Video To Analyze.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.Videocam,
                            contentDescription = "Video Selected",
                            modifier = Modifier.size(64.dp),
                            tint = Color(0xFF00C853)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Video Ready!",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Click the upload button below.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            (uploadState as? UploadPresentationUIState.Processing)?.let { state ->
                Spacer(modifier = Modifier.height(16.dp))
                ProcessingStatusCard(state)
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    videoUri?.let { uri ->
                        presentationViewModel.uploadPresentation( uri, presentationTitle)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = presentationTitle.isNotBlank() && videoUri != null && !isBusy,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3478E4))
            ) {
                if (uploadState is UploadPresentationUIState.Loading) {
                    Text("Uploading...", fontWeight = FontWeight.Bold)
                } else if (uploadState is UploadPresentationUIState.Processing) {
                    Text("Analyzing...", fontWeight = FontWeight.Bold)
                } else {
                    Text("Upload", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun ProcessingStatusCard(state: UploadPresentationUIState.Processing) {
    val stageText = when (state.stage) {
        "UPLOADING" -> "Sending video to AI..."
        "WAITING_ACTIVE" -> "AI is preparing your video..."
        "GENERATING" -> if (state.attempt > 1) "Analyzing presentation (retry ${state.attempt - 1})..." else "Analyzing presentation..."
        "SAVING" -> "Saving results..."
        else -> "Waiting for analysis..."
    }
    val minutes = state.elapsedSeconds / 60
    val seconds = (state.elapsedSeconds % 60).toString().padStart(2, '0')

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 3.dp)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(stageText, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.onSurface)
                Text(
                    "Elapsed $minutes:$seconds",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun PresentationUploadVideoViewPreview() {
    PresentationUploadVideoView(navController = rememberNavController())
}
