package com.example.ecpresent.ui.view.pages.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.view.components.elements.FeedbackText
import com.example.ecpresent.ui.view.components.elements.FeedbackTier

@Composable
fun PresentationFeedbackView(
    feedbackIndicatorData: List<Pair<String, String>>,
    feedbackSuggestionData: List<Pair<String, String>>
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ){
        Text(
            text = "Current Rating",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            style = TextStyle(color = Color(0XFF606060))
        )
        Spacer(modifier = Modifier.height(32.dp))

        FeedbackTier()
        Spacer(modifier = Modifier.height(32.dp))

        Column{
            feedbackIndicatorData.forEach { item ->
                FeedbackText(item.first, item.second)
            }
        }

        Text(
            text = "Suggestion",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(14.dp))
        Column{
            feedbackSuggestionData.forEach { items->
                Row (
                    modifier = Modifier.padding(bottom = 6.dp)
                ) {
                    Text(
                        text = items.first,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(end = 13.dp)
                    )
                    Text(
                        text = items.second,
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp,
                        lineHeight = 28.sp
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PresentationFeedbackPreview(){
    val dummyFeedbackIndicatorData = listOf(
        Pair("Intonation", "Your intonation still needs some work."),
        Pair("Posture", "Your posture still needs some work."),
        Pair("Expression", "Your expression still needs some work.")
    )
    val dummySuggestionData = listOf(
        Pair("1.", "Try adding fluctuations to your presentation!"),
        Pair("2.","Try to look at the audience!"),
        Pair("3.", "Try not to always look at your script!")
    )
    ECPresentTheme {
        PresentationFeedbackView(dummyFeedbackIndicatorData, dummySuggestionData)
    }
}

