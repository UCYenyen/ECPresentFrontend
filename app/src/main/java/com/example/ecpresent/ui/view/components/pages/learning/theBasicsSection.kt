package com.example.ecpresent.ui.view.components.pages.learning

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecpresent.R
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.view.components.elements.LearningVideoCard

@Composable
fun TheBasicsSection(navController: NavController) {
    // Data dummy untuk simulasi list
    val dummyData = listOf(
        Pair("How to present?", "In this module you will learn basic presentation skills."),
        Pair("Body Language", "Master your gestures and posture on stage."),
        Pair("Voice Control", "Learn how to project your voice clearly."),
        Pair("Visual Aids", "Designing effective slides for your audience.")
    )

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
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
                modifier = Modifier.clickable { navController.navigate(AppView.PresentationFeedback.name) }
            )
        }

        // Horizontal Scrollable List
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(dummyData) { item ->
                LearningVideoCard(
                    title = item.first,
                    description = item.second,
                    imageRes = R.drawable.ecpresenthero1
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TheBasicsSectionPreview() {
//    ECPresentTheme {
//        TheBasicsSection()
//    }
}