package com.example.ecpresent.ui.view.components.pages.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.uistates.LoginUIState
import com.example.ecpresent.ui.viewmodel.AuthViewModel

@Composable
fun SignInSection(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val loginState by authViewModel.loginUIState.collectAsState()

    LaunchedEffect(loginState) {
        if (loginState is LoginUIState.Success) {
            navController.navigate(AppView.Learning.name) {
                popUpTo(AppView.SignIn.name) { inclusive = true }
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
                text = "Sign In",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("Email", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = emailText,
                onValueChange = { emailText = it },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("examplename@gmail.com") },
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
                placeholder = { Text("your password") },
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                    IconButton (onClick = { passwordVisible = !passwordVisible }) {
                        Icon(imageVector = image, contentDescription = "Toggle password visibility")
                    }
                }
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
                onClick = { authViewModel.login(emailText, passwordText) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = loginState !is LoginUIState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3478E4))
            ) {
                if (loginState is LoginUIState.Loading) {
                    Text("Loading...", fontWeight = FontWeight.Bold)
                } else {
                    Text("Sign In", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                HorizontalDivider(modifier = Modifier.weight(1f), thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface)
                Text("Or", modifier = Modifier.padding(horizontal = 10.dp), fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
                HorizontalDivider(modifier = Modifier.weight(1f), thickness = 1.dp, color = MaterialTheme.colorScheme.onSurface)
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { authViewModel.continueAsGuest() },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = loginState !is LoginUIState.Loading,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3478E4))
            ) {
                Text("Continue as a guest", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Don't Have An Account? Sign Up",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier.fillMaxWidth().clickable(onClick = {navController.navigate(AppView.SignUp.name)}),
                textAlign = TextAlign.Center
            )
        }
    }
}
