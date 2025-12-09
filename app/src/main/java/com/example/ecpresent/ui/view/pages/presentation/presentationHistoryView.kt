package com.example.ecpresent.ui.view.pages.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.ecpresent.R

import com.example.ecpresent.ui.view.components.pages.presentation.presentationHistorySection


@Composable
fun presentationHistoryView(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(R.drawable.filler_top_right),
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopEnd).width(200.dp),
            contentScale = ContentScale.FillWidth
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp, vertical = 64.dp),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                Text("Presentations", modifier = Modifier.padding(start = 8.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            }
        }
        presentationHistorySection()
        Image(
            painter = painterResource(R.drawable.filler_bottom_left),
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomStart).width(200.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
@Preview(showBackground = true)
fun presentationHistoryViewPreview(){
    presentationHistoryView()
}