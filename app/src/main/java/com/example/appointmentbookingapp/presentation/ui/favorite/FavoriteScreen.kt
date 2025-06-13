package com.example.appointmentbookingapp.presentation.ui.favorite

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.presentation.state.UiState
import com.example.appointmentbookingapp.presentation.ui.components.DocCard
import com.example.appointmentbookingapp.presentation.ui.favorite.viewModel.FavoriteViewModel
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.SharedDoctorViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen(navController: NavHostController,
                   sharedDoctorViewModel: SharedDoctorViewModel = viewModel(),
) {
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val favoriteDoctorsState by favoriteViewModel.favoritesState.collectAsState()
    val isFavorite by favoriteViewModel.isFavorite.collectAsState()

    LaunchedEffect(Unit){
        favoriteViewModel.getFavorites()
    }
    val scope = rememberCoroutineScope()

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
                            .fillMaxSize()
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
                                favoriteViewModel.checkIfFavorite(doctor.id)
                                DocCard(doctor = doctor, onClick = {
                                    scope.launch {
                                        sharedDoctorViewModel.setSelectedDoctor(doctor)
                                    }
                                    navController.navigate("DoctorDetail")
                                }, isFavorite)
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    Text("Failed to load Favorite doctors", color = Color.Red)
                }

            }

        }

    }
}
@Preview
@Composable
fun FavoriteScreenPreview(){
    FavoriteScreen(navController = rememberNavController())
}
