package com.example.ecpresent.ui.view.components.pages.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ecpresent.R
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.uistates.ProfileUIState
import com.example.ecpresent.ui.viewmodel.AuthViewModel
import android.net.Uri


@Composable
fun PersonalInformationSection(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    val profileState by authViewModel.profileUIState.collectAsState()
    val profile = if (profileState is ProfileUIState.Success){
        (profileState as ProfileUIState.Success).data
    } else null

    // State buat Dialog Pop-up
    var showDialog by remember { mutableStateOf(false) }

    // --- LOGIKA UNTUK UPLOAD GAMBAR DARI GALERI ---
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            showDialog = false
        }
    }
    if (showDialog) {
        val currentId = profile?.avatar?.id?.toString()?.toIntOrNull() ?: 1

        AvatarSelectionDialog(
            authViewModel = authViewModel,
            currentAvatarId = currentId,
            onDismiss = { showDialog = false },
            onUploadClick = {
                // Buka Galeri (Hanya Gambar)
                photoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            contentAlignment = Alignment.BottomEnd, // Ikon pensil di pojok kanan bawah gambar
            modifier = Modifier.size(110.dp) // Ukuran area gambar
        ){
            AsyncImage(
                model = profile?.avatar?.imageUrl,
                contentDescription = "User Avatar",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.Gray, CircleShape),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(R.drawable.ic_launcher_foreground),
                error = painterResource(R.drawable.ic_launcher_background)
            )
            Box(
                modifier = Modifier
                    .size(36.dp) // Ukuran lingkaran pensil
                    .background(Color(0xFF3478E4), CircleShape) // Warna biru
                    .border(2.dp, Color.White, CircleShape)
                    .clickable { showDialog = true }, // KLIK DISINI BUAT BUKA DIALOG
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit Avatar",
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
        }


        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            Text(
                text = "Name",
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black
            )
            TextField(
                value = profile?.username ?: "Not Rendered",
                onValueChange = {},
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
                    fontWeight = FontWeight.Medium
                )
            )
        }

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
                value = profile?.email?: "Not Rendered",
                onValueChange = {},
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
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }
}
// --- POP UP PILIH GAMBAR (TANPA PASSWORD) ---
@Composable
private fun AvatarSelectionDialog(
    authViewModel: AuthViewModel,
    currentAvatarId: Int,
    onDismiss: () -> Unit,
    onUploadClick: () -> Unit // Callback buat upload
) {
    var selectedAvatarId by remember { mutableIntStateOf(if (currentAvatarId > 0) currentAvatarId else 1) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Ganti Foto Profil", fontWeight = FontWeight.Bold) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // OPSI 1: PILIH AVATAR PRESET
                Text("Pilih Avatar Kartun:", fontWeight = FontWeight.Medium)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    AvatarItem(id = 1, isSelected = selectedAvatarId == 1) { selectedAvatarId = 1 }
                    AvatarItem(id = 2, isSelected = selectedAvatarId == 2) { selectedAvatarId = 2 }
                    AvatarItem(id = 3, isSelected = selectedAvatarId == 3) { selectedAvatarId = 3 }
                }

                Divider()

                // OPSI 2: UPLOAD DARI GALERI
                Text("Atau Upload Sendiri:", fontWeight = FontWeight.Medium)
                Button(
                    onClick = onUploadClick,
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
                ) {
                    Text("Pilih Gambar dari Galeri")
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    // Update Avatar ID Langsung (TANPA PASSWORD)
                    // Note: Parameter password diisi string kosong "" karena kita hapus inputnya
                    authViewModel.updateAvatar(selectedAvatarId, "")
                    onDismiss()
                }
            ) {
                Text("Simpan Avatar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        }
    )
}

@Composable
private fun AvatarItem(id: Int, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray

    Box(
        modifier = Modifier
            .size(60.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .border(2.dp, borderColor, CircleShape)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        // Nanti ganti Text ini dengan Image Resource (R.drawable.avatar_1, dll)
        Text("Av-$id", fontWeight = FontWeight.Bold)
    }
}

@Preview(showBackground = true)
@Composable
private fun PersonalInformationSectionPreview() {
    ECPresentTheme {}
}