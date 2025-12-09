package com.example.ecpresent.ui.view.components.elements

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecpresent.ui.theme.ECPresentTheme

@Composable
fun PresentationHistoryCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xff3478E4)
        )
    ) {
        Column (modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp).fillMaxWidth()) {
            Text("24 February 2024", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Light)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                "Presentation Title",
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PresentationHistoryCardPreview() {
    ECPresentTheme {
        PresentationHistoryCard()    }
}