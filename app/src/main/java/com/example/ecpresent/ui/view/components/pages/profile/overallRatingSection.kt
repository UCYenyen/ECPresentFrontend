package com.example.ecpresent.ui.view.components.pages.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecpresent.ui.theme.ComponentBackground
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.view.components.elements.PresentationIndicatorSummaryCard

@Composable
fun OverallRatingSection() {
    val dummyDataIndicatorSummary = listOf(
        Pair( "S Tier", "Good Job"),
        Pair( "47% ^", "Intonation"),
        Pair( "48% ^", "Expression"),
        Pair( "43% ^", "Posture")
    )
    Card(
        colors = CardDefaults.cardColors(
            containerColor = ComponentBackground
        ),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 12.dp,
                spotColor = Color(0xFF000000),
                ambientColor = Color(0xFF000000)
            )
    ){
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ){
            Text(
                text = "Great Job !",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
            LazyRow (
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ){
                items(dummyDataIndicatorSummary) { item ->
                    PresentationIndicatorSummaryCard(
                        result = item.first,
                        topic = item.second
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun OverallRatingSectionPreview() {
    ECPresentTheme {
        OverallRatingSection()
    }
}