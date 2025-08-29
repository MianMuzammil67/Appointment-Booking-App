package com.example.appointmentbookingapp.presentation.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun LogOutDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        icon = {
            Icons.AutoMirrored.Filled.Logout
        },
        title = { Text("Log Out?") },
        text = { Text("Are sure you want to Log Out") },

        confirmButton = {
            TextButton(onConfirm) {
                Text("Log Out")
            }

        },
        dismissButton = {
            TextButton(onDismiss) {
                Text("Cancel")
            }
        }
    )
}