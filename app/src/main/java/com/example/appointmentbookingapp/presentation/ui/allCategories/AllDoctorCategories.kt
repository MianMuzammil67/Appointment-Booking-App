package com.example.appointmentbookingapp.presentation.ui.allCategories

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.domain.model.DoctorCategory
import com.example.appointmentbookingapp.presentation.state.UiState
import com.example.appointmentbookingapp.presentation.ui.home.components.CategoryItem
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.HomeViewModel

@Composable
fun AllDoctorCategories(navController: NavHostController) {
    val viewMode: HomeViewModel = hiltViewModel()

    val allCategories by viewMode.categories.collectAsState()
    var categoryList by remember { mutableStateOf(emptyList<DoctorCategory>()) }

    Scaffold(Modifier.fillMaxSize()) { contentPadding ->

        when (allCategories) {
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
                categoryList = (allCategories as UiState.Success<List<DoctorCategory>>).data
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            Modifier.padding(contentPadding)
        ) {
            items(categoryList.size) { index ->
                val item = categoryList[index]
                CategoryItem(item)
            }
        }
    }

}

@Preview
@Composable
fun AllDoctorCategoriesPreview() {
    AllDoctorCategories(navController = rememberNavController())
}
