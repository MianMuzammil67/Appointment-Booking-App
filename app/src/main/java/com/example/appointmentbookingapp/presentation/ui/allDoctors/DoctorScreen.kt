package com.example.appointmentbookingapp.presentation.ui.allDoctors

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.appointmentbookingapp.presentation.state.UiState
import com.example.appointmentbookingapp.presentation.ui.components.DocCard
import com.example.appointmentbookingapp.presentation.ui.favorite.viewModel.FavoriteViewModel
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.HomeViewModel
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.SharedDoctorViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoctorScreen(
    navController: NavHostController,
    homeViewModel: HomeViewModel = hiltViewModel(),
    sharedDoctorViewModel: SharedDoctorViewModel = viewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel()
) {
    val favoriteIds by favoriteViewModel.favoriteIds.collectAsState()

    val scope = rememberCoroutineScope()
    val allDoctors by homeViewModel.allDoctorState.collectAsState()

    Scaffold(
        Modifier
            .fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("All Doctors", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }) { contentPadding ->

        when (val state = allDoctors) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        "Failed to fetch server time.",
                        color = Color.Red
                    )
                }
            }

            is UiState.Success -> {
                val categoryList = state.data
                LazyColumn(
                    modifier = Modifier
                        .padding(contentPadding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)

                ) {
                    items(categoryList) { doctor ->
                        val isFavorite = favoriteIds.contains(doctor.id)
                        DocCard(
                            doctor = doctor, onClick = {
                                scope.launch {
                                    sharedDoctorViewModel.setSelectedDoctor(doctor)
                                }
                                navController.navigate("DoctorDetail")
                            }, isFavorite = isFavorite,
                            onToggleFavorite = { favoriteViewModel.toggleFavorite(doctor) })
                    }
                }
            }
        }
    }
}