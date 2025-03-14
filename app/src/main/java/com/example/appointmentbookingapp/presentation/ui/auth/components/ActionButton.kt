package com.example.appointmentbookingapp.presentation.ui.auth.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.appointmentbookingapp.R

@Composable
fun ActionButton (
    onClick : () -> Unit,
    buttonText : String
) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
            .padding(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.colorPrimary)
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(buttonText)
    }
}