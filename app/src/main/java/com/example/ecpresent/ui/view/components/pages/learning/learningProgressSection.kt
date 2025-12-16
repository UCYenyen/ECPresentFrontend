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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.ecpresent.ui.viewmodel.LearningViewModel

@Composable
fun LearningProgressSection(
    navController: NavController,
    showAll: Boolean = true,
    learningViewModel: LearningViewModel
) {
    val progressState by learningViewModel.learningProgressUIState.collectAsState()
    LaunchedEffect(Unit) {
        learningViewModel.getMyLearningProgresses()
    }

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

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = "No Progress Data Available",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    } else {
                        if (!showAll) {
                            item {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "My Learnings",
                                        style = MaterialTheme.typography.titleLarge,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    Text(
                                        text = "View More",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = Color(0xFF4A7DFF),
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier.clickable {
                                            navController.navigate(
                                                AppView.LearningProgresses.name
                                            )
                                        }
                                    )
                                }
                            }
                        }
                        items(state.data) {
                            progress ->
                            LearningProgressCard(
                                progress = progress,
                                navController = navController,
                                learningViewModel = learningViewModel
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