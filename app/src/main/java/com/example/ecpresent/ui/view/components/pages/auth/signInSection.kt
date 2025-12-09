package com.example.ecpresent.ui.view.components.pages.auth

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.theme.ECPresentTheme

@Composable
fun SignInSection(navController: NavController = rememberNavController()) {
    var emailText by remember { mutableStateOf("") }
    var passwordDummy by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp)
        ) {
            Text(
                text = "Sign In",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Email",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = emailText,
                onValueChange = { emailText = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("examplename@gmail.com", style = MaterialTheme.typography.titleMedium) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Password",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = passwordDummy,
                onValueChange = { passwordDummy = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("your password", style = MaterialTheme.typography.titleMedium) },
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3478E4)
                )
            ) {
                Text(
                    "Sign In",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "Or",
                    modifier = Modifier.padding(horizontal = 10.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.titleLarge,
                )
                HorizontalDivider(
                    modifier = Modifier.weight(1f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {navController.navigate(AppView.Landing.name)},
                modifier = Modifier.fillMaxWidth().height(50.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF3478E4)
                )
            ) {
                Text(
                    "Continue as a guest",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SignInSectionPreview() {
    ECPresentTheme {
        SignInSection()
    }
}