package com.example.appointmentbookingapp.presentation.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil3.compose.AsyncImage
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.domain.model.DoctorItem

@Composable
fun DocCard(
    doctor: DoctorItem,
    onClick: () -> Unit,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,

        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color("#D2EBE7".toColorInt()))
            .padding(8.dp)
            .clickable { onClick() },

        )
    {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(colorResource(R.color.muted_rose))

        ) {
            AsyncImage(
                model = doctor.imageUrl,
                contentDescription = null,
                Modifier
                    .matchParentSize()
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }
        Column(
            Modifier

                .padding(start = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = doctor.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    painter = if (isFavorite) {
                        painterResource(R.drawable.ic_fav_filled)
                    } else {
                        painterResource(R.drawable.ic_fav)
                    },
                    contentDescription = null,
                    Modifier
                        .size(24.dp)
                        .clickable {
                            onToggleFavorite()
                        },
                    tint = colorResource(R.color.colorPrimary),
                )

            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                thickness = 1.dp,
                color = Gray
            )
            Text(
                text = doctor.docCategory,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black,
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.5f),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Icon(
                        painter = painterResource(R.drawable.ic_rate),
                        contentDescription = null,
                        Modifier
                            .size(24.dp)
                            .padding(end = 3.dp),
                        tint = colorResource(R.color.yellow)
                    )
                    Text(
                        text = doctor.rating,
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .height(20.dp),
                        thickness = 1.dp,
                        color = Gray
                    )
                    Text(
                        text = "${doctor.reviewsCount} Reviews",
                        style = MaterialTheme.typography.bodySmall,
                        color = Gray

                    )
                }
                Text(
                    text = "Book",
                    style = MaterialTheme.typography.titleSmall,
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

@Preview
@Composable
fun DocCardPreview(modifier: Modifier = Modifier) {
    DocCard(doctor = DoctorItem(), { }, isFavorite = true,{} )
}


