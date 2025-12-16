package com.example.ecpresent.ui.view.components.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecpresent.ui.model.LearningProgress
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.viewmodel.AuthViewModel
import com.example.ecpresent.ui.viewmodel.LearningViewModel

@Composable
fun LearningProgressCard(
    progress: LearningProgress,
    navController: NavController,
    learningViewModel: LearningViewModel = viewModel()
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xff3478E4)
        ),
        onClick = {
            navController.navigate("${AppView.LearningProgresses.name}/${progress.id}")
        }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = learningViewModel.getYoutubeThumbnailUrl(progress.learning.videoUrl),
                contentDescription = null,
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
                    .background(Color.Black.copy(alpha = 0.2f)),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 14.dp)
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = progress.learning.title,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = progress.status,
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}