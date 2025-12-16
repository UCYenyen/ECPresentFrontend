package com.example.ecpresent.ui.view.pages.learning

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.uistates.LearningProgressUIState
import com.example.ecpresent.ui.viewmodel.ViewModel

@Composable
fun LearningProgressDetailView(
    progressId: Int,
    navController: NavController,
    viewModel: ViewModel = viewModel()
) {
    val progressState by viewModel.learningProgressUIState.collectAsState()
    val uriHandler = LocalUriHandler.current

    val progress = if (progressState is LearningProgressUIState.Success) {
        (progressState as LearningProgressUIState.Success).data.find { it.id == progressId.toString() }
    } else null

    if (progress != null) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surface)
        ) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp)
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = viewModel.getYoutubeThumbnailUrl(progress.learning.videoUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                try {
                                    uriHandler.openUri(progress.learning.videoUrl)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            },
                        contentScale = ContentScale.Crop,
                        alpha = 0.7f
                    )

                    Icon(
                        imageVector = Icons.Default.PlayCircle,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier
                            .size(64.dp)
                            .clickable {
                                try {
                                    uriHandler.openUri(progress.learning.videoUrl)
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = progress.learning.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(
                        modifier = Modifier.background(color = Color(0xFF2D6FF3), shape = RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = progress.status,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Description",
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Text(
                        text = progress.learning.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Started at: ${progress.createdAt}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        enabled = progress.status != "COMPLETED",
                        onClick = {
                            viewModel.markAsDoneLearningProgress(progressId.toString()) {
                                navController.navigate(AppView.LearningProgresses.name)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4A7DFF),
                            disabledContainerColor = Color.Gray,
                            disabledContentColor = Color.White
                        )
                    ) {
                        Text(
                            text = if  (progress.status == "COMPLETED") "Done" else "Mark As Done",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (progressState is LearningProgressUIState.Loading) {
                Text("Loading Progress...", color = Color.Black)
            } else {
                Text("Progress data not found", color = Color.Black)
            }
        }
    }
}