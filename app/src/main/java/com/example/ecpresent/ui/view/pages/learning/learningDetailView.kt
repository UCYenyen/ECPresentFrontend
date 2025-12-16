package com.example.ecpresent.ui.view.pages.learning

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.uistates.LearningDetailsUIState
import com.example.ecpresent.ui.viewmodel.AuthViewModel
import com.example.ecpresent.ui.viewmodel.LearningViewModel

@Composable
fun LearningDetailView(
    id: String,
    navController: NavController,
    learningViewModel: LearningViewModel = viewModel()
) {
    LaunchedEffect(Unit) {
        learningViewModel.getLearningById(id)
    }
    val learningDetailState by learningViewModel.learningDetailUIState.collectAsState()

    when (learningDetailState) {
        is LearningDetailsUIState.Initial -> {
            CircularProgressIndicator()
        }
        is LearningDetailsUIState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is LearningDetailsUIState.Success -> {
            LazyColumn(
                modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surface)
            ) {
                val learning = (learningDetailState as LearningDetailsUIState.Success).data

                item {
                    Box(modifier = Modifier.fillMaxWidth().height(220.dp)) {
                        AsyncImage(
                            model = learningViewModel.getYoutubeThumbnailUrl(learning.videoUrl),
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize().background(Color.Black),
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                item {
                    Column(modifier = Modifier.padding(horizontal = 16.dp).padding(top = 16.dp)) {
                        Text(
                            text = learning.title,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = learning.description,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            enabled = !(learningDetailState as LearningDetailsUIState.Success).isAdded,
                            onClick = {
                                learningViewModel.startLearning(learning.id) {
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
                                text = if ((learningDetailState as LearningDetailsUIState.Success).isAdded) "Added" else "Start Learning",
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
        is LearningDetailsUIState.Error ->{
            Box(
                modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colorScheme.surface),
                contentAlignment = Alignment.Center
            ) {
                Text("Learning data not found")
            }
        }
    }
}