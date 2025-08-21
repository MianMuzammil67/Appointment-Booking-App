package com.example.appointmentbookingapp.presentation.ui.doctorDetail

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Woman
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.presentation.ui.favorite.viewModel.FavoriteViewModel
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.SharedDoctorViewModel
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.DoctorChatSharedViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DocDetailScreen(
    navController: NavHostController,
    sharedDoctorViewModel: SharedDoctorViewModel = viewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
    doctorChatSharedViewModel: DoctorChatSharedViewModel = hiltViewModel(),
) {

    val currentDoctor by sharedDoctorViewModel.selectedDoctor.collectAsState()
// This code returns the hash code of the object, which helps in comparing two objects.

//    LaunchedEffect(sharedDoctorViewModel) {
//        println("DocDetailScreen ViewModel Hash: ${System.identityHashCode(sharedDoctorViewModel)}")
//        Log.d("DocDetail", "SelectedDoctor: $currentDoctor")
//    }

    val isFavorite by favoriteViewModel.isFavorite.collectAsState()

    LaunchedEffect(currentDoctor.id) {
        favoriteViewModel.checkIfFavorite(currentDoctor.id)
    }

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
                    IconButton(onClick = {
                        favoriteViewModel.toggleFavorite(currentDoctor)
                    }) {
                        Icon(
                            painter = if (isFavorite) {
                                painterResource(R.drawable.ic_fav_filled)
                            } else {
                                painterResource(R.drawable.ic_fav)
                            },
                            contentDescription = if (isFavorite) "Remove from favorites" else "Add to favorites",
                            tint = Color.Unspecified
                        )
                    }
                }
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
                        .size(32.dp)
                        .clickable {
                            doctorChatSharedViewModel.updateCurrentDoctor(currentDoctor)
                            navController.navigate("ChatScreen")
                        },
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
                .padding(16.dp)
                .fillMaxSize()
        )
        {
            TopCardSection(currentDoctor)
            Spacer(Modifier.height(24.dp))
            TabBarSection(currentDoctor)
            Spacer(Modifier.height(24.dp))
            AboutMeSection(currentDoctor)
            Spacer(Modifier.height(24.dp))
            SpokenLanguageSection(currentDoctor)
        }
    }
}

@Composable
fun TopCardSection(currentDoctor: DoctorItem) {
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
            AsyncImage(
                model = currentDoctor.imageUrl,
                contentDescription = null,
                Modifier
                    .matchParentSize()
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }

        Column {
            Text(
                text = currentDoctor.name,
                style = MaterialTheme.typography.titleLarge,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
            )
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                thickness = 1.dp,
                color = colorResource(R.color.gray)
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = currentDoctor.docCategory,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Fee: ${currentDoctor.consultationFee}",
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
fun TabBarSection(currentDoctor: DoctorItem) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val gender = currentDoctor.gender == "Male"
        ItemWithIcon(
            icon = if (gender) {
                Icons.Filled.Person
            } else {
                Icons.Filled.Woman
            },
//            icon = Icons.Filled.Person,
//            text = "200+\nPatients"
            text = currentDoctor.gender ?: "Unknown"
        )
        ItemWithIcon(
            imageResId = R.drawable.ic_experience,
            text = "${currentDoctor.experienceYears} Years\nExperience"
        )

        ItemWithIcon(
            icon = Icons.Filled.Star,
            text = "${currentDoctor.rating}\nRating"
        )

        ItemWithIcon(
            imageResId = R.drawable.feedback,
            text = "${currentDoctor.reviewsCount}\nReviews"
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
                .background(colorResource(R.color.light_gray))
                .padding(12.dp)
        ) {
            if (icon != null) {
                // For ImageVector (vector icons)
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    tint = colorResource(R.color.colorPrimary)
                )
            } else if (imageResId != null) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null,
                    modifier = Modifier.size(32.dp),
                    colorFilter = ColorFilter.tint(colorResource(R.color.colorPrimary))
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun AboutMeSection(currentDoctor: DoctorItem) {
    Column {
        Text(
            text = "About Me",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = currentDoctor.aboutDoctor,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
        )
    }
}

@Composable
fun SpokenLanguageSection(currentDoctor: DoctorItem) {

    Column {
        Text(
            text = "Spoken Languages",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(8.dp))
        val languageList = currentDoctor.languagesSpoken

        languageList?.forEach { language ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.padding(bottom = 4.dp)
            ) {
                Text(
//                    text = "⦿",
                    text = "➤",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(end = 8.dp)

                )
                Text(
                    text = language,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

        }

    }


}

@Preview
@Composable
fun DocScreenPreview() {
    DocDetailScreen(navController = rememberNavController())
}



