package com.example.ecpresent.ui.view.pages.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ecpresent.R
import com.example.ecpresent.ui.view.components.pages.presentation.PresentationHistorySection


@Composable
fun PresentationHistoryView(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
            .padding(horizontal = 12.dp),
    ) {
        PresentationHistorySection(showAll = true)
        Image(
            painter = painterResource(R.drawable.blue_filler_top_right),
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopEnd).width(200.dp),
            contentScale = ContentScale.FillWidth
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun PresentationHistoryViewPreview(){
    PresentationHistoryView()
}