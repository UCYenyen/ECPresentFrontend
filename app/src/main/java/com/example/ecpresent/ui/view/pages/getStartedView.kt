package com.example.ecpresent.ui.view.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
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
import com.example.ecpresent.R
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.view.components.elements.Carousel
import com.example.ecpresent.ui.view.components.elements.Title

@Composable
fun GetStartedView() {
    val myImages = listOf(
        R.drawable.ecpresenthero1,
        R.drawable.ecpresenthero2,
    )

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
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Title("EC Present", "Start presenting easily!")
            Carousel(
                imageList = myImages
            )
            Button(
                onClick = {},
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = Color.Black
                )
            ) {
                Text(
                    "Get Started",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Image(
            painter = painterResource(R.drawable.filler_bottom_left),
            contentDescription = null,
            modifier = Modifier.align(Alignment.BottomStart).width(200.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun GetStartedViewPreview() {
    ECPresentTheme {
        GetStartedView()
    }
}