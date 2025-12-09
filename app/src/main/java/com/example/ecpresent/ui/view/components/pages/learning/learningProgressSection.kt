package com.example.ecpresent.ui.view.components.pages.learning

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.view.components.elements.LearningProgressCard

@Composable
fun LearningProgressSection() {
    LearningProgressCard()
}

@Preview(showBackground = true)
@Composable
private fun LearningProgressSectionPreview() {
    ECPresentTheme {
        LearningProgressSection()
    }
}