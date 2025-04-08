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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.presentation.ui.components.DocCard
import com.example.appointmentbookingapp.presentation.ui.components.SearchDoctorField
import com.example.appointmentbookingapp.presentation.ui.homescreen.components.CategoryItem
import com.example.appointmentbookingapp.presentation.ui.homescreen.components.ImageSlider

@Preview
@Composable
fun HomeScreen() {
    val userName by remember { mutableStateOf("Mian Muzammil") }
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
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
                color = colorResource(id = R.color.black)
            )
            Text(
                text = "See All",
                style = MaterialTheme.typography.titleSmall,
                color = colorResource(id = R.color.gray)
            )
        }
        Spacer(Modifier.height(8.dp))

//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceAround,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            CategoryItem(
//                R.drawable.ic_cate_placeholder, "#DC9497", "Dentist"
//            )
//            CategoryItem(
//                R.drawable.ic_cate_placeholder, "#93C19E", "Dentist"
//            )
//            CategoryItem(
//                R.drawable.ic_cate_placeholder, "#F5AD7E", "Dentist"
//            )
//            CategoryItem(
//                R.drawable.ic_cate_placeholder, "#ACA1CD", "Dentist"
//            )
//
//        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CategoryItem(
                R.drawable.ic_cate_placeholder, "#DC9497", "Dentist"
            )
            CategoryItem(
                R.drawable.ic_cate_placeholder, "#93C19E", "Therapist"
            )
            CategoryItem(
                R.drawable.ic_cate_placeholder, "#F5AD7E", "Surgeon"
            )
            CategoryItem(
                R.drawable.ic_cate_placeholder, "#ACA1CD", "Cardiologist"
            )

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
                color = colorResource(id = R.color.black)
            )
            Text(
                text = "See All",
                style = MaterialTheme.typography.titleSmall,
                color = colorResource(id = R.color.gray)
            )
        }
        Spacer(Modifier.height(8.dp))
        DocCard()

        Spacer(Modifier.height(8.dp))
        DocCard()


//        DoctorItem()



    }

}