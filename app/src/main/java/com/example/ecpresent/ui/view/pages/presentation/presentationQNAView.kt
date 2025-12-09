package com.example.ecpresent.ui.view.pages.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun PresentationQNAView(){
    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 36.dp, vertical = 24.dp)

    ){
        Text(
            text = "Question 1",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun PresentationQNAPreview(){
    PresentationQNAView()
}
