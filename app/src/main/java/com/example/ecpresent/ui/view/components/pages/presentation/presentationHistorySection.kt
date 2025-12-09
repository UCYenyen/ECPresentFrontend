package com.example.ecpresent.ui.view.components.pages.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecpresent.ui.view.components.elements.PresentationHistoryCard

@Composable
fun PresentationHistorySection(showAll: Boolean, itemCount: Int) {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        if (!showAll) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Learning Progress",
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
            items(count = itemCount) {
                PresentationHistoryCard()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PresentationHistorySectionPreview() {
    PresentationHistorySection(showAll = false, itemCount = 5)
}
