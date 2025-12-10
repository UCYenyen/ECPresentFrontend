package com.example.ecpresent.ui.view.components.elements

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecpresent.ui.theme.ECPresentTheme

@Composable
fun PresentationIndicatorSummaryCard(
    result: String,
    topic: String
){
    Card (
        modifier = Modifier
            .width(126.dp)
            .height(82.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFFFFFFFF).copy(.8f)
        )
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                text = result,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                style = TextStyle(color = Color.Black)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = topic,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                style = TextStyle(color = Color.Black),
                maxLines = 1
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PresentationIndicatorSummaryCardPreview(){
    ECPresentTheme {
        PresentationIndicatorSummaryCard(
            "S Tier", "Good Job"
        )
    }
}
