package com.example.ecpresent.ui.view.components.pages.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecpresent.enum.GradeEnum
import com.example.ecpresent.ui.theme.ComponentBackground
import com.example.ecpresent.ui.uistates.AverageScoreUIState
import com.example.ecpresent.ui.view.components.elements.PresentationIndicatorSummaryCard
import com.example.ecpresent.ui.viewmodel.PresentationViewModel
import com.example.ecpresent.utils.GradeHelper

@Composable
fun OverallRatingSection(
    presentationViewModel: PresentationViewModel
) {
    val averageState by presentationViewModel.averageScoreState.collectAsState()

    LaunchedEffect(Unit) {
        presentationViewModel.getAverageScore()
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = ComponentBackground),
        modifier = Modifier
            .fillMaxWidth()
            .shadow(12.dp, spotColor = Color.Black, ambientColor = Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            var currentGrade = GradeEnum.UNKNOWN
            var gradeMessage = "Great Job !"
            var scoreList: List<Pair<Float, String>> = emptyList()

            if (averageState is AverageScoreUIState.Success) {
                val data = (averageState as AverageScoreUIState.Success).data

                currentGrade = GradeHelper.calculateGrade(data)
                gradeMessage = GradeHelper.getGradeMessage(currentGrade)

                scoreList = listOf(
                    data.averageIntonation to "Intonation",
                    data.averageExpression to "Expression",
                    data.averagePosture to "Posture"
                )
            }

            Text(
                text = "$gradeMessage!",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.White
            )

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    PresentationIndicatorSummaryCard(
                        result = currentGrade.toString(),
                        topic = gradeMessage
                    )
                }

                items(scoreList) { (score, topic) ->
                    PresentationIndicatorSummaryCard(
                        result = score.toString(),
                        topic = topic
                    )
                }
            }
        }
    }
}