package com.example.appointmentbookingapp.presentation.ui.doctorDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import com.example.appointmentbookingapp.R


@Preview
@Composable
fun DocDetailScreen() {

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .padding(16.dp)
    )
    {
        TittleBar {}
        Spacer(Modifier.height(16.dp))
        TopCard()
        Spacer(Modifier.height(24.dp))
        TabBar()
        Spacer(Modifier.height(24.dp))
        AboutMe()
        Spacer(Modifier.height(16.dp))


        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxHeight()) {
                Spacer(Modifier.weight(1f))
                BottomLayout()
            }
        }


    }
}


@Composable
fun TittleBar(
    onBackPressed: () -> Unit,
) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onBackPressed) {
            Image(
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                Modifier.size(32.dp)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Doctor Details",
            style = MaterialTheme.typography.titleLarge
        )
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { /* handle click */ }) {
            Image(
                painter = painterResource(R.drawable.ic_fav),
                contentDescription = "Favorite",
                Modifier.size(32.dp)
            )
        }
    }
}

@Composable
fun TopCard() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Center,
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

        Column(
//            Modifier
//                .padding(start = 8.dp)
        ) {
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
                // For PNG/SVG images (drawable resources)
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

//@Composable
//fun ItemWithIcon(icon: ImageVector, text: String) {
//    Column(
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Box(
//            Modifier
//                .clip(CircleShape)
//                .background(colorResource(R.color.light_gray))
//                .padding(12.dp)
//        ) {
//
//            Icon(
//                imageVector = icon,
//                contentDescription = null,
//                modifier = Modifier.size(32.dp),
//                tint = colorResource(R.color.colorPrimary)
//            )
//        }
//        Spacer(modifier = Modifier.height(4.dp))
//        Text(
//            text = text,
//            style = MaterialTheme.typography.bodyLarge,
//            color = Color.Black,
//            textAlign = TextAlign.Center,
//        )
//    }
//}

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
//            color = colorResource(R.color.black)
        )
    }

}

@Composable
fun BottomLayout() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
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
//                imageVector = Icons.Default.Add,
                painter = painterResource(R.drawable.ic_message),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.White),
            )
        }
        Button(
            onClick = { /* Handle Book Appointment click */ },
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



