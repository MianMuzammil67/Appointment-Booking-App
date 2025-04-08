package com.example.appointmentbookingapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.appointmentbookingapp.R

@Preview
@Composable
fun DoctorItem() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,

        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color("#D2EBE7".toColorInt()))
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp)
        ,
    )
    {
        Image(
            painter = painterResource(R.drawable.im_doctor),
            contentDescription = null,
            Modifier
                .height(200.dp)
                .weight(1f)
                .padding(end = 2.dp),
            contentScale = ContentScale.Crop,

            )

        Column(
            Modifier
                .weight(1.5f)
//                .padding(top = 32.dp)
                .padding(start = 2.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "DR.Pawan",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black
                )
                Icon(
                    painter = painterResource(R.drawable.ic_fav),
                    contentDescription = null,
                    Modifier.size(24.dp),
                    tint = colorResource(R.color.colorPrimary)
                )

            }
            Text(
                text = "Jorem ipsum dolor, consectetur adipiscing elit. Nunc v libero et velit interdum, ac  mattis.",
                style = MaterialTheme.typography.titleSmall,
                color = colorResource(R.color.gray),
            )

            Spacer(Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.Bottom,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Book",
                    style = MaterialTheme.typography.titleSmall,
                    color = colorResource(R.color.white),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(24.dp))
                        .background(colorResource(R.color.colorPrimary))
                        .padding(horizontal = 24.dp,vertical= 4.dp)
//                        .height(20.dp)
//                        .width(60.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(.5f),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Icon(
                        painter = painterResource(R.drawable.ic_rate),
                        contentDescription = null,
                        Modifier.size(24.dp).padding(end = 3.dp),
                        tint = colorResource(R.color.yellow)
                        )
                    Text(
                        text = "5.0",
                        style = MaterialTheme.typography.titleMedium,
                    )

                }
            }
        }


    }


}


