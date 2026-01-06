package com.example.ecpresent.ui.view.components.pages.presentation

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
import com.example.ecpresent.ui.uistates.PresentationIndexUIState
import com.example.ecpresent.ui.view.components.elements.PresentationHistoryCard
import com.example.ecpresent.ui.viewmodel.PresentationViewModel

@Composable
fun PresentationHistorySection(showAll: Boolean, presentationViewModel: PresentationViewModel, navController: NavController) {
    val presentationState by presentationViewModel.presentationIndexState.collectAsState()
    LaunchedEffect(Unit) {
        presentationViewModel.getPresentationHistory()
    }
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        when (val state = presentationState) {
            is PresentationIndexUIState.Success -> {
                if (!showAll) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Presentation History",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "View More",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF4A7DFF),
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.clickable { }
                        )
                    }
                }

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.data) { presentation ->
                        PresentationHistoryCard(presentation = presentation, navController = navController)
                    }
                }

            }
            is PresentationIndexUIState.Error -> {
                Text(
                    text = state.msg,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is PresentationIndexUIState.Loading -> {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            is PresentationIndexUIState.Initial -> {
                Text(
                    text = "Loading...",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            else -> {}
        }

    }
}

//@Preview(showBackground = true)
//@Composable
//private fun PresentationHistorySectionPreview() {
//    PresentationHistorySection(showAll = false, itemCount = 5)
//}
