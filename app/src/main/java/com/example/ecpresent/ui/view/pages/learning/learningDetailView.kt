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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.ecpresent.ui.uistates.LearningProgressUIState
import com.example.ecpresent.ui.uistates.LearningUIState
import com.example.ecpresent.ui.viewmodel.ViewModel

@Composable
fun LearningDetailView(
    id: String,
    navController: NavController,
    viewModel: ViewModel = viewModel()
) {
    val learningState by viewModel.learningUIState.collectAsState()
    val progressState by viewModel.learningProgressUIState.collectAsState()

    val learning = if (learningState is LearningUIState.Success) {
        (learningState as LearningUIState.Success).data.find { it.id == id }
    } else null

    val isStarted = if (progressState is LearningProgressUIState.Success) {
        (progressState as LearningProgressUIState.Success).data.any { it.learning.id == id }
    } else {
        false
    }

    if (learning != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth().height(220.dp)) {
                    AsyncImage(
                        model = viewModel.getYoutubeThumbnailUrl(learning.videoUrl),
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
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = learning.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        enabled = !isStarted,
                        onClick = {
                            viewModel.startLearning(learning.id) {
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
                            text = if (isStarted) "Added" else "Start Learning",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (learningState is LearningUIState.Loading) {
                Text("Loading...")
            } else {
                Text("Learning data not found")
            }
        }
    }
}