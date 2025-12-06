package com.example.ecpresent.ui.view.components.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun  Title(
    text: String,
    desciption: String,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(0.dp, Alignment.CenterVertically)) {
        Text(text, style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold, color = color)
        Text(desciption, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Medium, color = color)
    }
}

@Composable
@Preview(showBackground = true)
private fun TitlePreview() {
    Title(
        text = "EC Present",
        desciption = "Start presenting easily!"
    )
}