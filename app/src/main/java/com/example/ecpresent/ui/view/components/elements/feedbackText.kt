package com.example.ecpresent.ui.view.components.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecpresent.ui.theme.ECPresentTheme

@Composable
fun FeedbackText(
    indicatorType: String,
    indicatorDescription: String
){

    Column (
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = indicatorType,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = indicatorDescription,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            maxLines = 1
        )
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
@Preview(showBackground = true)
fun FeedbackTextPreview(){
    ECPresentTheme {
        FeedbackText(
            "Intonation",
            "Your intonation still needs some work"
        )
    }
}