package com.example.ecpresent.ui.view.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.uistates.LoginUIState
import com.example.ecpresent.ui.view.components.elements.Title
import com.example.ecpresent.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    authViewModel: AuthViewModel = viewModel(),
    navController: NavController
) {
    val loginState by authViewModel.loginUIState.collectAsState()

    var isSplashTimeFinished by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(2000)
        isSplashTimeFinished = true
    }

    LaunchedEffect(loginState, isSplashTimeFinished) {
        if (isSplashTimeFinished) {
            when (loginState) {
                is LoginUIState.Success -> {
                    navController.navigate(AppView.Learning.name) {
                        popUpTo(0) { inclusive = true }
                    }
                }
                is LoginUIState.Error -> {
                    navController.navigate(AppView.Landing.name) {
                        popUpTo(0) { inclusive = true }
                    }
                }
                else -> { }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background), // Pastikan background tidak transparan
        verticalArrangement = Arrangement.Center
    ) {
        Title("EC Present", "Start presenting easily!")
    }
}