package com.example.appointmentbookingapp.presentation.ui.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.appointmentbookingapp.R

@Composable
fun CategoryItem(
    image: Int,
    backgroundColor: String,
    categoryName: String
) {
    val color = Color(backgroundColor.toColorInt())
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            Modifier
                .padding(16.dp)
                .size(60.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(color)
            ,
            contentAlignment = Alignment.Center

        ) {

            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
            )
        }
        Text(
            text = categoryName,
            color = colorResource(R.color.gray),
            style = MaterialTheme.typography.titleSmall

        )
    }

}