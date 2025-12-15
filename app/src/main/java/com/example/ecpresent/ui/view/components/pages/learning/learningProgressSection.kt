package com.example.ecpresent.ui.view.components.pages.learning

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.uistates.LearningProgressUIState
import com.example.ecpresent.ui.view.components.elements.LearningProgressCard
import com.example.ecpresent.ui.viewmodel.ViewModel

@Composable
fun LearningProgressSection(
    navController: NavController,
    showAll: Boolean = true,
    viewModel: ViewModel
) {
    val progressState by viewModel.learningProgressUIState.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        when (val state = progressState) {
            is LearningProgressUIState.Success -> {
                LazyColumn(
                    modifier = Modifier.heightIn(max = 1000.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (state.data.isEmpty()) {
                        item {
                            if (showAll) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Learning Progress",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "No Progress Data Available",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    } else {
                        items(state.data) { progress ->
                            // Header hanya muncul sekali di item pertama atau logic bisa disesuaikan
                            // Di sini saya hapus header di dalam loop agar tidak berulang

                            // PERBAIKAN: Mengirimkan parameter yang dibutuhkan ke Card
                            LearningProgressCard(
                                progress = progress,
                                navController = navController,
                                viewModel = viewModel
                            )
                        }
                    }
                }
            }
            is LearningProgressUIState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is LearningProgressUIState.Loading -> {
                Text(
                    text = "Loading Progress...",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            else -> {
                Text(
                    text = "No Progress Data Available",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}