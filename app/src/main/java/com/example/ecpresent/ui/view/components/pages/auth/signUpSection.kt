package com.example.ecpresent.ui.view.components.pages.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.uistates.LoginUIState
import com.example.ecpresent.ui.viewmodel.AuthViewModel

@Composable
fun SignUpSection(
    navController: NavController,
    authViewModel: AuthViewModel = viewModel()
) {
    var usernameText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var confirmPasswordText by remember { mutableStateOf("") }

    val loginState by authViewModel.loginUIState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is LoginUIState.Success) {
            navController.navigate(AppView.Learning.name) {
                popUpTo(AppView.SignUp.name) { inclusive = true }
            }
            authViewModel.resetLoginState()
        }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth().padding(32.dp)) {
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Username", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = usernameText,
                onValueChange = { usernameText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Username") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Email", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = emailText,
                onValueChange = { emailText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("examplename@gmail.com") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Password", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = passwordText,
                onValueChange = { passwordText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password123") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text("Confirm Password", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = confirmPasswordText,
                onValueChange = { confirmPasswordText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password123") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(32.dp))

            if (loginState is LoginUIState.Error) {
                Text(
                    text = (loginState as LoginUIState.Error).message,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            Button(
                onClick = {
                    authViewModel.register(usernameText, emailText, passwordText, confirmPasswordText)
                },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = loginState !is LoginUIState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3478E4))
            ) {
                if (loginState is LoginUIState.Loading) {
                    Text("Loading...", fontWeight = FontWeight.Bold)
                } else {
                    Text("Sign Up", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignUpSectionPreview() {
    ECPresentTheme {
        val dummyNavController = rememberNavController()
        SignUpSection(navController = dummyNavController)
    }
}