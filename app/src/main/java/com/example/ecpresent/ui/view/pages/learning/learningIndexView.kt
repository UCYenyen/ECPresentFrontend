package com.example.ecpresent.ui.view.pages.learning

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecpresent.R
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.view.components.elements.Title
import com.example.ecpresent.ui.view.components.pages.auth.SignInSection
import com.example.ecpresent.ui.view.components.pages.learning.LearningProgressSection
import com.example.ecpresent.ui.view.components.pages.learning.TheBasicsSection

@Composable
fun LearningIndexView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 12.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.blue_filler_top_right),
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopEnd).fillMaxWidth(0.8f),
            contentScale = ContentScale.FillWidth
        )

        TheBasicsSection()
        LearningProgressSection()
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LearningIndexViewPreview() {
    ECPresentTheme {
        LearningIndexView()
    }
}