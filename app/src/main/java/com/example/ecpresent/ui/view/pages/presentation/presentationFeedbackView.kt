package com.example.ecpresent.ui.view.pages.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.uistates.FeedbackUIState
import com.example.ecpresent.ui.uistates.QnAUIState
import com.example.ecpresent.ui.view.components.elements.FeedbackText
import com.example.ecpresent.ui.viewmodel.PresentationViewModel

@Composable
fun PresentationFeedbackView(
    navController: NavController,
    presentationViewModel: PresentationViewModel = viewModel(),
    presentationId: String
) {

    val feedbackState by presentationViewModel.feedbackState.collectAsState()
    val notes by presentationViewModel.feedbackNotes.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(feedbackState) {
        if (feedbackState is FeedbackUIState.Deleted) {
            Toast.makeText(context, "Presentation discarded", Toast.LENGTH_SHORT).show()
            navController.navigate(AppView.Presentation.name) {
                popUpTo(AppView.Presentation.name) { inclusive = true }
            }
        }
    }


    if (feedbackState is FeedbackUIState.Loading) {
        Box(
            modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        when(val state = feedbackState){
            is FeedbackUIState.Initial -> {
                LaunchedEffect(Unit) {
                    presentationViewModel.getFinalFeedback(presentationId)
                }
            }
            is FeedbackUIState.Success ->{
                val feedbackData = state.data
                
                Text(
                    text = "Presentation Result",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Score Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp).fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Grade",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                        Text(
                            text = feedbackData.grade ?: "-",
                            fontSize = 48.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Overall Score: ${String.format("%.1f", feedbackData.overallRating?.toDouble())}",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Detail Scores
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    ScoreColumn("Video", feedbackData.videoScore)
                    ScoreColumn("Audio", feedbackData.audioScore ?: 0.0)
                    ScoreColumn("Expression", feedbackData.expression)
                }

                Divider(modifier = Modifier.padding(vertical = 24.dp))

                // Suggestions
                SectionTitle("Video Suggestion")
                Text(
                    text = feedbackData.videoSuggestion,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                SectionTitle("Audio Suggestion")
                Text(
                    text = feedbackData.audioSuggestion ?: "No specific audio suggestions.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Divider(modifier = Modifier.padding(vertical = 24.dp))

                // Personal Notes
                SectionTitle("Personal Notes")
                OutlinedTextField(
                    value = notes,
                    onValueChange = { presentationViewModel.onNotesChanged(it) },
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                    label = { Text("Write your self-evaluation here...") },
                    shape = RoundedCornerShape(12.dp),
                    minLines = 3
                )

                Button(
                    onClick = { presentationViewModel.updateNotes() },
                    enabled = notes.isNotEmpty(),
                    modifier = Modifier.align(Alignment.End).padding(top = 8.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (feedbackState is FeedbackUIState.NotesUpdated) "Saved!" else "Save Notes")
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
            is FeedbackUIState.Error -> {
                Text(
                    text = (feedbackState as FeedbackUIState.Error).msg,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
            else -> {
                Box(modifier = Modifier.fillMaxSize().height(200.dp), contentAlignment = Alignment.Center) {
                    Text("No Data Available", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
fun ScoreColumn(title: String, score: Any) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = String.format("%.1f", score),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = title,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(
        text = text,
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 4.dp)
    )
}

//@Composable
//@Preview(showBackground = true)
//private fun PresentationFeedbackViewPreview() {
//    PresentationFeedbackView(navController = rememberNavController())
//}