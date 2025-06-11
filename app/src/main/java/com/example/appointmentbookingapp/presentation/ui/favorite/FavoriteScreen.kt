package com.example.appointmentbookingapp.presentation.ui.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.presentation.state.UiState
import com.example.appointmentbookingapp.presentation.ui.components.DocCard
import com.example.appointmentbookingapp.presentation.ui.favorite.viewModel.FavoriteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavController) {
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()

    val favoriteDoctorsState by favoriteViewModel.favoritesState.collectAsState()


//    val doctorList = listOf(
//        DoctorItem(
//            id = "doc_001",
//            name = "Dr. Amelia Carter",
//            aboutDoctor = "Expert in women's health and gynecology with 12 years of experience.",
//            imageUrl = "https://img.freepik.com/free-photo/female-doctor-hospital-with-stethoscope_23-2148827774.jpg",
//            rating = "4.8",
//            docCategory = "Gynecologist",
//            isFavorite = false,
//            experienceYears = 12,
//            consultationFee = "$80",
////                isOnlineAvailable = true,
//            languagesSpoken = listOf("English", "Spanish"),
//            gender = "Female",
//            reviewsCount = 150
//        ),
//        DoctorItem(
//            id = "doc_002",
//            name = "Dr. Nathan Kim",
//            aboutDoctor = "Cardiologist with a patient-first approach and over a decade of experience.",
//            imageUrl = "https://www.hjhospitals.org/photos/doctors/_MG_1223t.jpg",
//            rating = "4.6",
//            docCategory = "Cardiologist",
//            isFavorite = false,
//            experienceYears = 11,
//            consultationFee = "$100",
////        isOnlineAvailable = false,
//            languagesSpoken = listOf("English", "Korean"),
//            gender = "Male",
//            reviewsCount = 200
//        ),
//        DoctorItem(
//            id = "doc_003",
//            name = "Dr. Priya Mehta",
//            aboutDoctor = "Pediatrician who makes kids feel at ease with compassionate care.",
//            imageUrl = "https://imgcdn.stablediffusionweb.com/2024/11/8/7446cd96-3e65-46fe-9cf3-5b096275ad49.jpg",
//            rating = "4.9",
//            docCategory = "Pediatrician",
//            isFavorite = false,
//            experienceYears = 9,
//            consultationFee = "$70",
////        isOnlineAvailable = true,
//            languagesSpoken = listOf("English", "Hindi", "Gujarati"),
//            gender = "Female",
//            reviewsCount = 180
//        ),
//        DoctorItem(
//            id = "doc_004",
//            name = "Dr. Daniel Wu",
//            aboutDoctor = "Dermatologist helping patients with skincare and advanced treatments.",
//            imageUrl = "https://jerrysusa.com/wp-content/uploads/2014/11/doctor-profile-02-250x375.jpg",
//            rating = "4.7",
//            docCategory = "Dermatologist",
//            isFavorite = false,
//            experienceYears = 10,
//            consultationFee = "$90",
////        isOnlineAvailable = true,
//            languagesSpoken = listOf("English", "Mandarin"),
//            gender = "Male",
//            reviewsCount = 130
//        ),
//        DoctorItem(
//            id = "doc_005",
//            name = "Dr. Sara Thompson",
//            aboutDoctor = "Orthopedic specialist with deep expertise in joint and bone care.",
//            imageUrl = "https://adaptcommunitynetwork.org/wp-content/uploads/2023/09/person-placeholder-500x367.jpg",
//            rating = "4.5",
//            docCategory = "Orthopedic",
//            isFavorite = false,
//            experienceYears = 8,
//            consultationFee = "$85",
////        isOnlineAvailable = false,
//            languagesSpoken = listOf("English"),
//            gender = "Female",
//            reviewsCount = 120
//        ),
//        DoctorItem(
//            id = "doc_006",
//            name = "Dr. Ahmed Al-Farsi",
//            aboutDoctor = "Neurologist known for detailed diagnosis and patient-centric care.",
//            imageUrl = "https://adaptcommunitynetwork.org/wp-content/uploads/2023/09/person-placeholder-500x367.jpg",
//            rating = "4.6",
//            docCategory = "Neurologist",
//            isFavorite = false,
//            experienceYears = 15,
//            consultationFee = "$110",
////        isOnlineAvailable = true,
//            languagesSpoken = listOf("English", "Arabic"),
//            gender = "Male",
//            reviewsCount = 210
//        )
//    )
//    val favoriteDoctor = doctorList.filter { it.isFavorite }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Appointment", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },

        ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp),

            ) {
            when (favoriteDoctorsState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }

                }

                is UiState.Success -> {
                    val doctors = (favoriteDoctorsState as UiState.Success<List<DoctorItem>>).data
                    if (doctors.isEmpty()) {
                        Text(
                            "No favorite doctors found.",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }else {
                        LazyColumn(modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp))
                        {
                            items(doctors) { doctor ->
                                Spacer(modifier = Modifier.height(8.dp))
                                DocCard(doctor = doctor, onClick = {})
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    Text("Failed to load Favorite doctors", color = Color.Red)
                }

            }


//            items(favoriteDoctorsState.value) { doctor ->
//                Spacer(modifier = Modifier.height(8.dp))
//                DocCard(doctor = doctor) {}
//            }

        }

    }
}
