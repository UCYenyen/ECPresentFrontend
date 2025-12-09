package com.example.ecpresent.ui.view.pages.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ecpresent.ui.view.components.elements.CameraScreen

@Composable
fun PresentationUploadVideoView() {
    CameraScreen()
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun PresentationUploadVideoViewPreview() {
    PresentationUploadVideoView()
}