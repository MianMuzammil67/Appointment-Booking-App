package com.example.appointmentbookingapp.presentation.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import com.example.appointmentbookingapp.R

@Preview
@Composable
fun DocCard() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,

        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color("#D2EBE7".toColorInt()))
            .padding(8.dp),
//            .padding(top = 8.dp)
    )
    {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(12.dp))
//                .height(100.dp)
//                .width(100.dp)
                .background(colorResource(R.color.muted_rose))
//                .weight(.8f)
//                .wrapContentSize()

//                .padding(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.im_doctor),
                contentDescription = null,
                Modifier
//                    .height(100.dp)
//                    .width(100.dp)
//                    .size(100.dp)
                    .matchParentSize()
                    .padding(start = 2.dp, end = 2.dp, top = 2.dp)

                    .align(Alignment.Center),
//                contentScale = ContentScale.Fit,


            )
        }

        Column(
            Modifier
//                .weight(1.5f)
//                .padding(top = 32.dp)
                .padding(start = 8.dp)
//                .offset(x= 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Dr. Andrew",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    painter = painterResource(R.drawable.ic_fav),
                    contentDescription = null,
                    Modifier.size(24.dp),
                    tint = colorResource(R.color.colorPrimary)
                )

            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp),
                thickness = 1.dp,
                color = Gray
            )
            Text(
                text = "Dentist",
                style = MaterialTheme.typography.bodyLarge,
//                color = colorResource(R.color.gray),
            )

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
//                Text(
//                    text = "Fee: 12$ ",
//                    style = MaterialTheme.typography.titleSmall,
//                    color = colorResource(R.color.white),
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(12.dp))
////                        .clip(RoundedCornerShape(24.dp))
//                        .background(colorResource(R.color.colorPrimary))
//                        .padding(horizontal = 24.dp, vertical = 4.dp)
////                        .height(20.dp)
////                        .width(60.dp)
//                )
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
                        text = "4.3",
                        style = MaterialTheme.typography.bodySmall,
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .padding(horizontal = 4.dp)
                            .height(20.dp),
                        thickness = 1.dp,
                        color = Gray
                    )
                    Text(
                        text = "405 Reviews",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }


                Text(
                    text = "Book",
                    style = MaterialTheme.typography.titleSmall,
                    color = colorResource(R.color.white),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
//                        .clip(RoundedCornerShape(24.dp))
                        .background(colorResource(R.color.colorPrimary))
                        .padding(horizontal = 24.dp, vertical = 4.dp)
//                        .height(20.dp)
//                        .width(60.dp)
                )
            }
        }


    }


}


