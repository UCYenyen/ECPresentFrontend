package com.example.ecpresent.ui.view.components.elements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ShowChart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecpresent.ui.theme.ComponentBackground
import com.example.ecpresent.ui.theme.MainBackground
import com.example.ecpresent.ui.theme.PresentActionButtonBackground
import com.example.ecpresent.ui.theme.PresentActionButtonStroke

@Composable
fun TapToAnswerCard(){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = ComponentBackground
        ),
        modifier = Modifier
            .height(568.dp)
            .width(360.dp),
        shape = RoundedCornerShape(12.dp)
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            OutlinedIconButton(
                border = BorderStroke(
                    width = 4.95.dp,
                    color = PresentActionButtonStroke
                ),
                onClick = {  },
                modifier = Modifier
                    .width(120.dp)
                    .height(120.dp)
                    .background(color = PresentActionButtonBackground, shape = CircleShape)
            ) {
                Icon(
                    contentDescription = "Analytics",
                    imageVector = Icons.AutoMirrored.Filled.ShowChart,
                    modifier = Modifier
                        .size(48.dp),
                    tint = PresentActionButtonStroke
                )
            }

            Spacer(
                modifier = Modifier
                    .height(10.dp)
            )

            Text(
                text= "Tap to answer",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MainBackground
            )
        }
    }

}

@Composable
@Preview(showBackground = true)
fun TapToAnswerCardPreview(){
    TapToAnswerCard()
}