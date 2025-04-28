package com.example.appointmentbookingapp.presentation.ui.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil3.compose.AsyncImage
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.domain.model.DoctorCategory

@Composable
fun CategoryItem(
    category: DoctorCategory,
    onClick: (String) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.clickable {
            onClick(category.label)
        }) {
        Box(
            Modifier
                .padding(16.dp)
                .size(60.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(Color(category.backgroundColor.toColorInt())),
            contentAlignment = Alignment.Center

        ) {
            AsyncImage(
                model = category.categoryIcon,
                contentDescription = null,
                modifier = Modifier
                    .size(42.dp)
            )
        }
        Text(
            text = category.label,
            color = colorResource(R.color.gray),
            style = MaterialTheme.typography.titleSmall
        )
    }

}