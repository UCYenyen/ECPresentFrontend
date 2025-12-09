package com.example.ecpresent.ui.view.components.pages.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecpresent.ui.view.components.elements.PresentationHistoryCard

@Composable
fun presentationHistorySection(){
    PresentationHistoryCard()
}

@Preview(showBackground = true)
@Composable
private fun presentationHistorySectionPreview(){
    presentationHistorySection()
}