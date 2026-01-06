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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecpresent.ui.theme.ComponentBackground
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.uistates.AverageScoreUIState
import com.example.ecpresent.ui.uistates.ProfileUIState
import com.example.ecpresent.ui.view.components.elements.PresentationIndicatorSummaryCard
import com.example.ecpresent.ui.viewmodel.AuthViewModel
import com.example.ecpresent.ui.viewmodel.PresentationViewModel

@Composable
fun OverallRatingSection(
    presentationViewModel: PresentationViewModel
) {
    val averageState by presentationViewModel.averageScoreState.collectAsState()

    LaunchedEffect(Unit) {
        presentationViewModel.getAverageScore()
    }
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
            when (val state = averageState) {
                is AverageScoreUIState.Loading -> {
                    Text("Loading scores...", fontSize = 14.sp)
                }
                is AverageScoreUIState.Error -> {
                    Text("Error: ${state.msg}", color = Color.Red, fontSize = 14.sp)
                }
                is AverageScoreUIState.Success -> {
                    val data = state.data
                    val scoreList = listOf(
                        data.averageIntonation to "Intonation",
                        data.averageExpression to "Expression",
                        data.averagePosture to "Posture"
                    )

                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(scoreList) { (score, topic) ->
                            PresentationIndicatorSummaryCard(
                                result = score.toString(),
                                topic = topic
                            )
                        }
                    }
                }
                else -> {}
            }
        }
    }

}

//@Preview(showBackground = true)
//@Composable
//private fun OverallRatingSectionPreview() {
//    ECPresentTheme {
//        OverallRatingSection()
//    }
//}