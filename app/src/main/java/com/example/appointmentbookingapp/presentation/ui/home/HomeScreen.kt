package com.example.appointmentbookingapp.presentation.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.domain.model.BannerItem
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.presentation.state.UiState
import com.example.appointmentbookingapp.presentation.ui.components.DocCard
import com.example.appointmentbookingapp.presentation.ui.components.SearchDoctorField
import com.example.appointmentbookingapp.presentation.ui.home.components.CategoryItem
import com.example.appointmentbookingapp.presentation.ui.home.components.ImageSlider

@Composable
fun HomeScreen(navController: NavHostController) {

    val homeViewModel: HomeViewModel = hiltViewModel()

    val userName by homeViewModel.userName.collectAsState()
    val profileImageUrl by homeViewModel.profileImageUrl.collectAsState()
    val bannerState by homeViewModel.bannerFlow.collectAsState()
    val doctorState by homeViewModel.doctorState.collectAsState()

    var search by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            HomeHeaderSection(userName = userName, profileUrl = profileImageUrl)
            Spacer(modifier = Modifier.height(16.dp))

            SearchSection(search, onSearchChange = { search = it })
            Spacer(modifier = Modifier.height(16.dp))

            BannerSection(bannerState)
            Spacer(modifier = Modifier.height(16.dp))

            CategorySection()
            Spacer(modifier = Modifier.height(16.dp))

            DoctorSection(doctorState) {
                navController.navigate("DoctorDetail")
            }
        }
    }

}

@Composable
fun HomeHeaderSection(userName: String?, profileUrl: String?) {
    Row(
        modifier = Modifier.fillMaxWidth(),
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
        Image(
            painter = painterResource(id = R.drawable.icon_bell),
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
        is UiState.Loading ->{
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
fun CategorySection() {
    val categories = listOf(
        CategoryData(R.drawable.ic_cate_placeholder, "#DC9497", "Dentist"),
        CategoryData(R.drawable.ic_cate_placeholder, "#93C19E", "Therapist"),
        CategoryData(R.drawable.ic_cate_placeholder, "#F5AD7E", "Surgeon"),
        CategoryData(R.drawable.ic_cate_placeholder, "#ACA1CD", "Cardiologist")
    )

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
            color = colorResource(id = R.color.gray)
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        categories.forEach {
            CategoryItem(
                image = it.icon,
                backgroundColor = it.color,
                categoryName = it.label
            )
        }
    }
}

@Composable
fun DoctorSection(
    state: UiState<List<DoctorItem>>,
    onDoctorClick: () -> Unit
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
            color = colorResource(id = R.color.gray)
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
                .sortedByDescending { it.rating }
                .take(4)

            topDoctors.forEach { doctor ->
                Spacer(modifier = Modifier.height(8.dp))
                DocCard(doctor = doctor, onClick = onDoctorClick)
            }
        }

        is UiState.Error -> Text("Failed to load doctors", color = Color.Red)
    }
}

data class CategoryData(val icon: Int, val color: String, val label: String)

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}