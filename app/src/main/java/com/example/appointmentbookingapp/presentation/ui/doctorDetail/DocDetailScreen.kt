package com.example.appointmentbookingapp.presentation.ui.doctorDetail

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocDetailScreen(navController: NavHostController) {
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Doctor Details", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "back")

                    }
                },
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(painter = painterResource(R.drawable.ic_fav), contentDescription = "add to Favorite")

                    }
                },
                colors = TopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = Color.Black,
                    actionIconContentColor = Color.Black,
                    navigationIconContentColor = Color.Black,
                    scrolledContainerColor = Color.White,
                )
            )
        },
        bottomBar = {

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .navigationBarsPadding()

            ) {
                Box(
                    Modifier
                        .clip(CircleShape)
                        .background(color = colorResource(R.color.colorPrimary))
                        .padding(16.dp)
                        .size(32.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_message),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(Color.White),
                    )
                }
                Button(
                    onClick = { navController.navigate("BookAppointment") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colorResource(id = R.color.colorPrimary),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {

                    Text(
                        text = "Book Appointment",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                }


            }


        }


    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .background(Color.White)
                .padding(16.dp)
                .fillMaxSize()
        )
        {
            TopCard()
            Spacer(Modifier.height(24.dp))
            TabBar()
            Spacer(Modifier.height(24.dp))
            AboutMe()
        }
    }
}


@Composable
fun TopCard() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp),

        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color("#D2EBE7".toColorInt()))
            .padding(horizontal = 8.dp, vertical = 12.dp),
    )
    {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource(R.color.muted_rose))
        ) {
            Image(
                painter = painterResource(R.drawable.im_doctor),
                contentDescription = null,
                Modifier
                    .matchParentSize()
                    .padding(start = 2.dp, end = 2.dp, top = 2.dp)
                    .align(Alignment.Center),
            )
        }

        Column {
            Text(
                text = "Dr. Andrew",
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                thickness = 1.dp,
                color = Gray
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Dentist",
                style = MaterialTheme.typography.bodyLarge,

                )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Fee: 12$",
                    style = MaterialTheme.typography.titleMedium,
                    color = colorResource(R.color.white),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorResource(R.color.colorPrimary))
                        .padding(horizontal = 24.dp, vertical = 4.dp)

                )
            }
        }
    }
}

@Composable
fun TabBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ItemWithIcon(
            icon = Icons.Filled.Person,
            text = "200+\nPatients"
        )
        ItemWithIcon(
            imageResId = R.drawable.ic_experience,
            text = "10+\nExperience"
        )

        ItemWithIcon(
            icon = Icons.Filled.Star,
            text = "3.4\nRating"
        )

        ItemWithIcon(
            imageResId = R.drawable.feedback,
            text = "23\nReviews"
        )
    }

}


@Composable
fun ItemWithIcon(
    icon: ImageVector? = null,
    imageResId: Int? = null,
    text: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            Modifier
                .clip(CircleShape)
                .background(colorResource(R.color.light_gray)) // Circle background
                .padding(12.dp)
        ) {
            if (icon != null) {
                // For ImageVector (vector icons)
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = colorResource(R.color.colorPrimary) // You can customize the tint here
                )
            } else if (imageResId != null) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    colorFilter = ColorFilter.tint(colorResource(R.color.colorPrimary)) // Optional tint
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun AboutMe() {
    Column {

        Text(
            text = "About Me",
            style = MaterialTheme.typography.titleLarge,
            color = colorResource(R.color.black),
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = "Dr. David Patel, a dedicated cardiologist, brings a wealth of experience to Golden Gate Cardiology Center in Golden Gate, CA.",
            style = MaterialTheme.typography.bodyMedium,
        )
    }

}

@Preview
@Composable
fun DocScreenPreview(modifier: Modifier = Modifier) {
    DocDetailScreen(navController = rememberNavController())
}



