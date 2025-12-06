package com.example.ecpresent.ui.view.pages.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecpresent.R
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.view.components.elements.Title
import com.example.ecpresent.ui.view.components.pages.auth.SignUpSection

@Composable
fun SignUpView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(R.drawable.filler_top_right),
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopEnd).fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )

        Title("EC Present", "Start presenting easily!")

        SignUpSection()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun SignUpViewPreview() {
    ECPresentTheme {
        SignUpView()
    }
}