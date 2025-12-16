package com.example.ecpresent.ui.view.components.pages.profile

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage // Pastikan library Coil sudah di-sync
import com.example.ecpresent.ui.model.User
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.viewmodel.AuthViewModel

@Composable
fun PersonalInformationSection(
    user: User
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        // 1. Bagian Avatar (Menggunakan Coil untuk URL)
        AsyncImage(
            model = user.imageUrl,
            contentDescription = "User Avatar",
            modifier = Modifier
                .size(100.dp) // Ukuran Avatar
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape), // List tipis biar rapi
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 2. Form Name (Desain SAMA PERSIS dengan kode Abang)
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = "Username",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            TextField(
                value = user.username, // Data dari Backend masuk sini
                onValueChange = {}, // Read Only
                readOnly = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.LightGray,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.DarkGray,
                    focusedContainerColor = Color.LightGray // Tambahan biar warnanya konsisten pas diklik
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }

        // 3. Form Email (Desain SAMA PERSIS dengan kode Abang)
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = "Email",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            TextField(
                value = user.email, // Data dari Backend masuk sini
                onValueChange = {}, // Read Only
                readOnly = true,
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.LightGray,
                    disabledIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    errorIndicatorColor = Color.Transparent,
                    focusedPlaceholderColor = Color.DarkGray,
                    focusedContainerColor = Color.LightGray
                ),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PersonalInformationSectionPreview() {
    ECPresentTheme {
//        PersonalInformationSection(
//            username = "Bryan Fernando Dinata",
//            email = "bryanfernandodinata@gmail.com",
//            avatarUrl = "https://res.cloudinary.com/demo/image/upload/sample.jpg"
//        )
    }
}