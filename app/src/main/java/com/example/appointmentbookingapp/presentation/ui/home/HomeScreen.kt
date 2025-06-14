package com.example.appointmentbookingapp.presentation.ui.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.domain.model.BannerItem
import com.example.appointmentbookingapp.domain.model.DoctorCategory
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.presentation.state.UiState
import com.example.appointmentbookingapp.presentation.ui.components.DocCard
import com.example.appointmentbookingapp.presentation.ui.components.SearchDoctorField
import com.example.appointmentbookingapp.presentation.ui.favorite.viewModel.FavoriteViewModel
import com.example.appointmentbookingapp.presentation.ui.home.components.CategoryItem
import com.example.appointmentbookingapp.presentation.ui.home.components.ImageSlider
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.HomeViewModel
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.SharedDoctorViewModel
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavHostController,
    sharedDoctorViewModel: SharedDoctorViewModel = viewModel(),
    homeViewModel: HomeViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel()

) {

    val userName by homeViewModel.userName.collectAsState()
    val profileImageUrl by homeViewModel.profileImageUrl.collectAsState()
    val bannerState by homeViewModel.bannerFlow.collectAsState()
    val doctorState by homeViewModel.topDoctorState.collectAsState()
    val categoryState by homeViewModel.categories.collectAsState()

    val favoriteIds by favoriteViewModel.favoriteIds.collectAsState()

    var search by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HomeHeaderSection(userName = userName, profileUrl = profileImageUrl)
            Spacer(modifier = Modifier.height(16.dp))

            SearchSection(search, onSearchChange = { search = it })
            Spacer(modifier = Modifier.height(16.dp))

            BannerSection(bannerState)
            Spacer(modifier = Modifier.height(16.dp))

            CategorySection(categoryState, navController)
            Spacer(modifier = Modifier.height(16.dp))

            DoctorSection(
                state = doctorState,
                onDoctorClick = { currentDoctor ->
                    scope.launch {
                        sharedDoctorViewModel.setSelectedDoctor(currentDoctor)
                    }
                    navController.navigate("DoctorDetail")
                    Log.d("HomeScreen", "HomeScreen: ${currentDoctor.id}")
                },
                onSeeAllClicked = {
                    navController.navigate("DoctorScreen")
                },
                favoriteViewModel = favoriteViewModel,
                favoriteIds = favoriteIds
            )
            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}

@Composable
fun HomeHeaderSection(userName: String?, profileUrl: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.demo_user),
            contentDescription = null,
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text("Hi, Welcome Back,", color = colorResource(id = R.color.gray))
            Text(
                text = userName ?: "Guest",
                color = colorResource(id = R.color.black),
                style = MaterialTheme.typography.titleMedium
            )
        }
//        Image(
////            painter = painterResource(id = R.drawable.icon_bell),
//            icon = Icons.Filled.Notifications,
//            contentDescription = null,
//            modifier = Modifier.size(24.dp)
//        )
        Icon(
            imageVector = Icons.Filled.Notifications,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun SearchSection(value: String, onSearchChange: (String) -> Unit) {
    SearchDoctorField(
        value = value,
        onValueChange = onSearchChange
    )
}

@Composable
fun BannerSection(state: UiState<List<BannerItem>>) {
    when (state) {
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
            val imageUrls = state.data.map { it.imageUrl }
            ImageSlider(imageUrls = imageUrls)
        }

        is UiState.Error -> Text("Error: ${state.message}", color = Color.Red)
    }
}

@Composable
fun CategorySection(
    categoryState: UiState<List<DoctorCategory>>,
    navController: NavHostController
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Categories",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black)
        )
        Text(
            text = "See All",
            style = MaterialTheme.typography.titleSmall,
            color = colorResource(id = R.color.gray),
            modifier = Modifier.clickable {
                navController.navigate("AllDoctorCategories")
            }
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {

        when (categoryState) {
            is UiState.Success -> {
                Log.d("HomeScreen", categoryState.data.toString())

                categoryState.data.forEach { category ->
                    CategoryItem(category = category) { itemName ->
                        navController.navigate("DoctorScreen")
                    }
                }
            }

            is UiState.Error -> {
                Text("Error: ${categoryState.message}", color = Color.Red)
                Log.d("HomeScreen", categoryState.message)
            }

            else -> {

            }
        }
    }
}

@Composable
fun DoctorSection(
    state: UiState<List<DoctorItem>>,
    onDoctorClick: (DoctorItem) -> Unit,
    onSeeAllClicked: () -> Unit,
    favoriteViewModel: FavoriteViewModel,
    favoriteIds: Set<String>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Doctors",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black)
        )
        Text(
            text = "See All",
            style = MaterialTheme.typography.titleSmall,
            color = colorResource(id = R.color.gray),
            modifier = Modifier.clickable {
                onSeeAllClicked()
            }
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    when (state) {
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
            val topDoctors = state.data

            topDoctors.forEach { doctor ->
                Spacer(modifier = Modifier.height(8.dp))
                val favorite = favoriteIds.contains(doctor.id)

                DocCard(
                    doctor = doctor,
                    onClick = { onDoctorClick(doctor) },
                    isFavorite = favorite,
                    onToggleFavorite = {favoriteViewModel.toggleFavorite(doctor)}
                )
            }
        }

        is UiState.Error -> Text("Failed to load doctors", color = Color.Red)
    }
}

//data class CategoryData(val icon: Int, val color: String, val label: String)

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}