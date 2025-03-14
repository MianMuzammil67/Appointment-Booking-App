package com.example.appointmentbookingapp.presentation.ui.auth.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.appointmentbookingapp.R

@Composable
fun ImageWithBorder(image: Int) {
    Box(

        modifier = Modifier
            .size(54.dp)
            .border(1.dp, colorResource(id =R.color.gray), shape = CircleShape),

    ) {
        Image(
            painter = painterResource(id = image), // Replace with your image
            contentDescription = null,
            modifier = Modifier
                .size(46.dp) // The image size should be 50dp
                .padding(1.dp)
                .align(Alignment.Center)// 1dp space between the image and the border
        )
    }
}
