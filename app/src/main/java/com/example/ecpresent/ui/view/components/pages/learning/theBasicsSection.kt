package com.example.ecpresent.ui.view.components.pages.learning

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.ecpresent.ui.uistates.LearningUIState
import com.example.ecpresent.ui.view.components.elements.LearningVideoCard
import com.example.ecpresent.ui.viewmodel.AuthViewModel
import com.example.ecpresent.ui.viewmodel.LearningViewModel

@Composable
fun TheBasicsSection(navController: NavController, learningViewModel: LearningViewModel, showAll: Boolean = false) {
    val learningState by learningViewModel.learningUIState.collectAsState()

    LaunchedEffect(Unit) {
        learningViewModel.getAllLearnings()
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        if(!showAll)
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "The basics",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "View More",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4A7DFF),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { navController.navigate(AppView.Learnings.name) }
                )
            }
        }


        when (val state = learningState) {
            is LearningUIState.Success -> {
                if(!showAll){
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.data) { learning ->
                            LearningVideoCard(
                                learning = learning,
                                navController = navController,
                                learningViewModel = learningViewModel
                            )
                        }
                    }
                }else{
                    LazyColumn (verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(state.data) { learning ->
                            LearningVideoCard(
                                learning = learning,
                                navController = navController,
                                learningViewModel = learningViewModel,
                                showingAll = true
                            )
                        }
                    }
                }

            }
            is LearningUIState.Error -> {
                Text(
                    text = state.message,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is LearningUIState.Loading -> {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            else -> {}
        }
    }
}