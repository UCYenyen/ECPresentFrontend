package com.example.ecpresent.ui.view.components.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.theme.MainBackground

@Composable
fun FeedbackTier(){
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(MainBackground),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)

    ) {
        Box(
            modifier = Modifier
                .size(75.dp)
                .background(color = Color(0XFFD9D9D9), shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "S",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Overall",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "S tier is a good, Great Job!",
                fontWeight = FontWeight.Medium,
                fontSize = 20.sp
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun FeedbackTierPreview(){
    ECPresentTheme {
        FeedbackTier()
    }
}