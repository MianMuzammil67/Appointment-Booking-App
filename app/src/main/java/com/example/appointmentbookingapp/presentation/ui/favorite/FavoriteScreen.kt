package com.example.appointmentbookingapp.presentation.ui.favorite

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.presentation.ui.components.DocCard

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun FavoriteScreen() {

    val doctorList = listOf(
        DoctorItem(
            id = "1",
            name = "Dr. Andrew",
            description = "Dentist",
            imageUrl = "",
            rating = "4.3",
            docCategory = "Dentist",
            isFavorite = true
        ),
        DoctorItem(
            id = "2",
            name = "Dr. Emma",
            description = "Pediatrician",
            imageUrl = "",
            rating = "4.8",
            docCategory = "Pediatrics",
            isFavorite = false
        ),
        DoctorItem(
            id = "3",
            name = "Dr. Michael",
            description = "Cardiologist",
            imageUrl = "",
            rating = "4.1",
            docCategory = "Cardiology",
            isFavorite = true
        )
    )

    val favoriteDoctor = doctorList.filter { it.isFavorite }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Appointment", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back */ }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },

        ) { innerPadding ->
        LazyColumn(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp),


            ) {
            items(favoriteDoctor) { doctor ->
                Spacer(modifier = Modifier.height(8.dp))
                DocCard()
            }

        }

    }
}
