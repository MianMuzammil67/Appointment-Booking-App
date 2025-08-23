package com.example.appointmentbookingapp.presentation.ui.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun DeleteItemDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {

    AlertDialog(
        onDismissRequest = onDismiss,

        title = { Text("Delete this chat?") },
        text = { Text("Are sure you want to delete") },
        confirmButton = {

            TextButton(onDismiss) {
                Text("Cancel")
            }
            TextButton(onClick = onConfirm) {
                Text("Delete")
            }
        }


    )


}
