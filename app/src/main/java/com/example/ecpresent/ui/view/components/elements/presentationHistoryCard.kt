package com.example.ecpresent.ui.view.components.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ecpresent.ui.model.Presentation
import com.example.ecpresent.ui.model.PresentationStatus
import com.example.ecpresent.ui.route.AppView
import com.example.ecpresent.ui.viewmodel.PresentationViewModel

@Composable
fun PresentationHistoryCard(presentation: Presentation, presentationViewModel: PresentationViewModel = viewModel(), navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 4.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xff3478E4)
        ),
        onClick = {
            if (presentation.status == PresentationStatus.ONGOING) {
                navController.navigate("${AppView.FollowUpQuestion.name}/${presentation.id}")
            } else {
                navController.navigate("${AppView.PresentationFeedback.name}/${presentation.id}")
            }
        }
    ) {
        Column (modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp).fillMaxWidth()) {
            Text(presentation.createdAt, color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Light)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                presentation.title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}