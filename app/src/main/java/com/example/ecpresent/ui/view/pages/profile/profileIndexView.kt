package com.example.ecpresent.ui.view.pages.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecpresent.R
import com.example.ecpresent.ui.theme.ECPresentTheme
import com.example.ecpresent.ui.view.components.pages.profile.OverallRatingSection
import com.example.ecpresent.ui.view.components.pages.profile.PersonalInformationSection

@Composable
fun ProfileIndexView() {
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
        Column (
            modifier = Modifier
                .padding(16.dp),
        ){
            Text(
                text = "Personal Information",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                modifier = Modifier.padding(bottom = 6.dp),
                color = Color.Black
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
            ){
                PersonalInformationSection()
                OverallRatingSection()
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileIndexViewPreview() {
    ECPresentTheme {
        ProfileIndexView()
    }
}