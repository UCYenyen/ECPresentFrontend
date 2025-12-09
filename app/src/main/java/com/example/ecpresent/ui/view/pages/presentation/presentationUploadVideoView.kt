package com.example.ecpresent.ui.view.pages.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ecpresent.ui.view.components.elements.CameraScreen

@Composable
fun PresentationUploadVideoView(navController: NavController) {
    CameraScreen(onNavigateBack = {
        navController.popBackStack()
    })
}

@Composable
@Preview(showSystemUi = true, showBackground = true)
private fun PresentationUploadVideoViewPreview() {
    PresentationUploadVideoView(navController = rememberNavController())
}