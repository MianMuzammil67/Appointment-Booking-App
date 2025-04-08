package com.example.appointmentbookingapp.presentation.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.appointmentbookingapp.R

@Preview
@Composable
fun SearchDoctorField() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .padding(16.dp)
    ) {
        TextField(
            value = "",  // You can bind this to your state
            onValueChange = {},
//            label = { Text("Search a Doctor") },
            placeholder = { Text("Search a Doctor") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_search),  // Replace with your search icon
                    contentDescription = "Search Icon"
                )
            },
            trailingIcon = {
                IconButton(onClick = { /* Handle voice click */ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_voice),  // Replace with your voice icon
                        contentDescription = "Voice Icon"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                }
            ),
            shape = MaterialTheme.shapes.small,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            )
        )
    }
}
