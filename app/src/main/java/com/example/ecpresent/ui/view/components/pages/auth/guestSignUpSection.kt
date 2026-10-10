package com.example.ecpresent.ui.view.components.pages.auth

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.ecpresent.data.local.DataStoreManager
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.theme.ButtonColor
import com.example.ecpresent.ui.uistates.LoginUIState
import com.example.ecpresent.ui.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.first

@Composable
fun GuestSignUpSection(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val context = LocalContext.current
    val dataStoreManager = remember { DataStoreManager(context) }
    var hasGuestToken by remember { mutableStateOf<Boolean?>(null) }

    var usernameText by remember { mutableStateOf("") }
    var emailText by remember { mutableStateOf("") }
    var passwordText by remember { mutableStateOf("") }
    var confirmPasswordText by remember { mutableStateOf("") }

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    val loginState by authViewModel.loginUIState.collectAsState()

    LaunchedEffect(Unit) {
        val token = dataStoreManager.tokenFlow.first()
        hasGuestToken = !token.isNullOrEmpty()
    }

    LaunchedEffect(loginState) {
        if (loginState is LoginUIState.Success) {
            navController.navigate(AppView.Learning.name) {
                popUpTo(AppView.GuestSignUp.name) { inclusive = true }
            }
        }
    }

    if (hasGuestToken == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    if (hasGuestToken == false) {
        LaunchedEffect(Unit) {
            navController.navigate(AppView.SignIn.name) {
                popUpTo(AppView.GuestSignUp.name) { inclusive = true }
            }
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Please continue as guest first",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Redirecting...",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
        }
        return
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        LazyColumn {
            item {
                Column(modifier = Modifier.fillMaxWidth().padding(32.dp)) {
                    Text(
                        text = "Complete Your Registration",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    OutlinedTextField(
                        value = usernameText,
                        onValueChange = { usernameText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Username") },
                        shape = RoundedCornerShape(12.dp),
                        enabled = loginState !is LoginUIState.Loading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = emailText,
                        onValueChange = { emailText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Email") },
                        shape = RoundedCornerShape(12.dp),
                        enabled = loginState !is LoginUIState.Loading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = passwordText,
                        onValueChange = { passwordText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Password") },
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                val icon = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                                Icon(icon, contentDescription = null)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        enabled = loginState !is LoginUIState.Loading
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = confirmPasswordText,
                        onValueChange = { confirmPasswordText = it },
                        modifier = Modifier.fillMaxWidth(),
                        label = { Text("Confirm Password") },
                        visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                                val icon = if (confirmPasswordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                                Icon(icon, contentDescription = null)
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        enabled = loginState !is LoginUIState.Loading
                    )

                    if (loginState is LoginUIState.Error) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = (loginState as LoginUIState.Error).message,
                            color = Color.Red,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            authViewModel.guestRegister(usernameText, emailText, passwordText, confirmPasswordText)
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        enabled = loginState !is LoginUIState.Loading,
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer)

                    ) {
                        if (loginState is LoginUIState.Loading) {
                            Text("Loading...")
                        } else {
                            Text("Complete Registration")
                        }
                    }
                }
            }
        }
    }
}