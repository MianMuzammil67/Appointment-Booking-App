package com.example.appointmentbookingapp.presentation.ui.homescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.presentation.ui.components.DocCard
import com.example.appointmentbookingapp.presentation.ui.components.SearchDoctorField
import com.example.appointmentbookingapp.presentation.ui.homescreen.components.CategoryItem
import com.example.appointmentbookingapp.presentation.ui.homescreen.components.ImageSlider

@Composable
fun HomeScreen(navController: NavHostController) {
    val userName by remember { mutableStateOf("Mian Muzammil") }

    val categories = listOf(
        CategoryData(R.drawable.ic_cate_placeholder, "#DC9497", "Dentist"),
        CategoryData(R.drawable.ic_cate_placeholder, "#93C19E", "Therapist"),
        CategoryData(R.drawable.ic_cate_placeholder, "#F5AD7E", "Surgeon"),
        CategoryData(R.drawable.ic_cate_placeholder, "#ACA1CD", "Cardiologist")
    )
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
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())

        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
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

                    Text(
                        "Hi,Welcome Back,", color = colorResource(id = R.color.gray)
                    )
                    Text(
                        text = userName,
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

            Spacer(modifier = Modifier.height(16.dp))

            SearchDoctorField()

            Spacer(modifier = Modifier.height(16.dp))

            val images = listOf(
                R.drawable.doc1, R.drawable.doc2, R.drawable.doc3
            )

            ImageSlider(images = images)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = "Categories",
                    style = MaterialTheme.typography.titleLarge,
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "See All",
                    style = MaterialTheme.typography.titleSmall,
                    color = colorResource(id = R.color.gray)
                )
            }
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                categories.forEach { category ->
                    CategoryItem(
                        image = category.icon,
                        backgroundColor = category.color,
                        categoryName = category.label,
                    )

                }

            }
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically

            ) {
                Text(
                    text = "Doctors",
                    style = MaterialTheme.typography.titleLarge,
                    color = colorResource(id = R.color.black),
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "See All",
                    style = MaterialTheme.typography.titleSmall,
                    color = colorResource(id = R.color.gray)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            doctorList.forEach { docCard ->
                Spacer(modifier = Modifier.height(8.dp))
                DocCard(docCard){
                    navController.navigate("DoctorDetail")
                }
            }
        }

    }
}

data class CategoryData(val icon: Int, val color: String, val label: String)

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}