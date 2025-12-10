package com.example.ecpresent.ui.view.components.pages.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ecpresent.ui.theme.ECPresentTheme

@Composable
fun PersonalInformationSection() {
    val dummyFieldInputPlaceholderText = listOf(
        Pair( "Name", "Bryan Fernando Dinata"),
        Pair("Email" ,"bryanfernandodinata@gmail.com")
    )
    Column(
        modifier = Modifier.fillMaxWidth()
    ){
        Text(
            text = "Personal Information",
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(
            modifier = Modifier
                .height(14.dp)
        )
        LazyColumn(){
            items(dummyFieldInputPlaceholderText) { item ->
                Text(
                    text = item.first,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(
                    modifier = Modifier
                        .height(8.dp)
                )
                TextField(
                    value = item.second,
                    onValueChange = {},
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.LightGray,
                        disabledIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        errorIndicatorColor = Color.Transparent,
                        focusedPlaceholderColor = Color.DarkGray
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(
                        12.dp
                    ),
                    textStyle = TextStyle(
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PersonalInformationSectionPreview() {
    ECPresentTheme {
        PersonalInformationSection()
    }
}