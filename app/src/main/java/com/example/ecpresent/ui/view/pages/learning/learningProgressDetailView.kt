package com.example.ecpresent.ui.view.pages.learning

import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ecpresent.ui.uistates.LearningProgressUIState
import com.example.ecpresent.ui.viewmodel.ViewModel

@Composable
fun LearningProgressDetailView(
    progressId: Int,
    navController: NavController,
    viewModel: ViewModel = viewModel()
) {
    val progressState by viewModel.learningProgressUIState.collectAsState()

    val progress = if (progressState is LearningProgressUIState.Success) {
        (progressState as LearningProgressUIState.Success).data.find { it.id == progressId }
    } else null

    if (progress != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                val videoId = viewModel.extractYoutubeId(progress.learning.videoUrl)
                if (videoId != null) {
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(16f / 9f)
                            .background(Color.Black),
                        factory = { context ->
                            WebView(context).apply {
                                settings.javaScriptEnabled = true
                                settings.loadWithOverviewMode = true
                                settings.useWideViewPort = true
                                webChromeClient = WebChromeClient()
                                loadUrl("https://www.youtube.com/embed/$videoId")
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
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color = if(progress.status == "COMPLETED") Color.Green else Color(0xFFFFC107),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(
                            text = progress.status,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text("Description", fontWeight = FontWeight.Bold)
                    Text(
                        text = progress.learning.description,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Started at: ${progress.createdAt}",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (progressState is LearningProgressUIState.Loading) {
                Text("Loading Progress...")
            } else {
                Text("Progress data not found")
            }
        }
    }
}