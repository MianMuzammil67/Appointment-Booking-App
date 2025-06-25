package com.example.appointmentbookingapp.presentation.ui.appointment

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.appointmentbookingapp.R

@Composable
fun AppointmentItem() {
    Column(

        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(8.dp)
            .clickable { },

        ) {
        Text(
            text = "May 14, 2023 - 10.00 AM",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        HorizontalDivider(thickness = 2.dp)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),

            ) {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(colorResource(R.color.muted_rose))

            ) {
                Image(
                    modifier = Modifier
                        .matchParentSize()
                        .align(Alignment.Center),
                    painter = painterResource(R.drawable.im_doctor),
                    contentDescription = null,
                    contentScale = ContentScale.Crop

                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = "Dr. David Patel",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Cardiologist",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )

            }
        }
        HorizontalDivider(thickness = 2.dp)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.gray200),
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text("Cancel")

            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {},
            ) {
                Text("Reschedule")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAppointmentItem() {
    AppointmentItem()
}