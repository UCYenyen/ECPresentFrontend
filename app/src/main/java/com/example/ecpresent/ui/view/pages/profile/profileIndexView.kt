package com.example.ecpresent.ui.view.pages.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ecpresent.R
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.uistates.ProfileUIState
import com.example.ecpresent.ui.view.components.pages.profile.OverallRatingSection
import com.example.ecpresent.ui.view.components.pages.profile.PersonalInformationSection
import com.example.ecpresent.ui.viewmodel.AuthViewModel
import com.example.ecpresent.ui.viewmodel.PresentationViewModel

@Composable
fun ProfileIndexView(
    authViewModel: AuthViewModel = viewModel(),
    presentationViewModel: PresentationViewModel = viewModel(),
    navController: NavController
) {

    val profileState by authViewModel.profileUIState.collectAsState()
    LaunchedEffect(Unit) {
        if (profileState is ProfileUIState.Initial) {
            authViewModel.getProfileById()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.surface)
    ) {
        Image(
            painter = painterResource(R.drawable.blue_filler_top_right),
            contentDescription = null,
            modifier = Modifier.align(Alignment.TopEnd).fillMaxWidth(0.8f),
            contentScale = ContentScale.FillWidth
        )

        when (profileState) {
            is ProfileUIState.Loading, is ProfileUIState.Initial -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is ProfileUIState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = (profileState as ProfileUIState.Error).message,
                        color = Color.Red
                    )
                }
            }
            is ProfileUIState.Success -> {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                ) {
                    item {
                        Text(
                            text = "Personal Information",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 24.sp,
                            modifier = Modifier.padding(bottom = 6.dp),
                            color = Color.Black
                        )
                    }
                    item {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(24.dp),
                        ) {
                            PersonalInformationSection(
                                authViewModel = authViewModel,
                                navController = navController
                            )
                            OverallRatingSection(
                                presentationViewModel = presentationViewModel
                            )
                            Button(
                                onClick = {
                                    authViewModel.logout(onSuccess = {
                                        navController.navigate(AppView.Landing.name) {
                                            popUpTo(0) { inclusive = true }
                                            launchSingleTop = true
                                        }
                                    })
                                },
                                modifier = Modifier.fillMaxWidth().height(50.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3478E4))
                            ) {
                                Text("Logout", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
            else -> {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileIndexViewPreview() {
    ECPresentTheme {
        ProfileIndexView(
            authViewModel = viewModel(),
            navController = rememberNavController()
        )
    }
}