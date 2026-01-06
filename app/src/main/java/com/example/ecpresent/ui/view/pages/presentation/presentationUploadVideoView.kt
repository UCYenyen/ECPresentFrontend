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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CloudUpload
import androidx.compose.material.icons.outlined.Videocam
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.drawWithContent
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
import com.example.ecpresent.ui.viewmodel.PresentationViewModel
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun PresentationUploadVideoView(navController: NavController, presentationViewModel: PresentationViewModel = viewModel()) {
    var presentationTitle by remember { mutableStateOf("") }
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val stroke = Stroke(
        width = 8f,
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
    )

    val videoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            videoUri = uri
        }
    )

    val uploadState by presentationViewModel.uploadPresentationUIState.collectAsState()

    LaunchedEffect(uploadState) {
        when (val state = uploadState) {
            is UploadPresentationUIState.Success -> {
                Toast.makeText(context, "Upload successful!", Toast.LENGTH_SHORT).show()
                navController.navigate(AppView.FollowUpQuestion.name+"/${state.data.id}")
                presentationViewModel.resetState()
            }
            is UploadPresentationUIState.Error -> {
                Toast.makeText(context, state.message, Toast.LENGTH_LONG).show()
                presentationViewModel.resetState()
            }
            else -> {}
        }
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
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Start
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
                    .weight(1f) // Mengambil sisa ruang yang tersedia
                    .clickable { videoPickerLauncher.launch("video/*") }
                    .drawWithContent {
                        drawContent()
                        drawRoundRect(
                            color = Color.Black,
                            style = stroke,
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(16.dp.toPx())
                        )
                    },
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black.copy(alpha = 0.025f)
                ),
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (videoUri == null) {
                        Icon(
                            imageVector = Icons.Outlined.CloudUpload,
                            contentDescription = "Upload Video",
                            modifier = Modifier.size(64.dp),
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Tap To Upload Video",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
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
                            tint = Color.Black
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Video Ready!", style = MaterialTheme.typography.titleLarge, color = Color.Black)
                        Text(
                            text = "Click Upload to proceed.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "--------------------Or--------------------",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Click here to select another video.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    videoUri?.let { uri ->
                        presentationViewModel.uploadPresentation(uri, presentationTitle)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = presentationTitle.isNotBlank() && videoUri != null && uploadState !is UploadPresentationUIState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3478E4))
            ) {
                if (uploadState is UploadPresentationUIState.Loading) {
                    Text("Uploading...", fontWeight = FontWeight.Bold)
                } else {
                    Text("Upload", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun PresentationUploadVideoViewPreview() {
    PresentationUploadVideoView(navController = rememberNavController())
}
