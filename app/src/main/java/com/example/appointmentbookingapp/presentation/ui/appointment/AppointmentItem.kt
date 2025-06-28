package com.example.appointmentbookingapp.presentation.ui.appointment

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import coil3.compose.AsyncImage
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.domain.model.Appointment
import com.example.appointmentbookingapp.domain.model.DoctorItem

@Composable
fun AppointmentItem(
    appointment: Appointment,
    onClick: () -> Unit,
    appointmentViewModel: AppointmentViewModel
) {

    var doctor by remember { mutableStateOf<DoctorItem?>(null) }
    var isLoading by remember { mutableStateOf(true) }


    val appointmentDate = appointment.appointmentDate
    val appointmentTime = appointment.timeSlot

    LaunchedEffect(appointment.doctorId) {
        isLoading = true
        try {
            doctor = appointmentViewModel.getDoctorById(appointment.doctorId)
        } catch (e: Exception) {
            doctor = null
        }
        isLoading = false
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color("#D2EBE7".toColorInt()))
            .padding(8.dp)
            .clickable { onClick() },
    ) {
        Text(
//            text = "May 14, 2023 - 10.00 AM",
            text = "$appointmentDate - $appointmentTime",
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
                AsyncImage(
                    modifier = Modifier
                        .matchParentSize()
                        .align(Alignment.Center),
                    model = doctor?.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = when {
                        isLoading -> "Loading..."
                        doctor != null -> doctor!!.name
                        else -> "Unknown Doctor"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = when {
                        isLoading -> "Loading..."
                        doctor != null -> doctor!!.docCategory
                        else -> "Unknown Specialty"
                    },
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
                Text(
                    "Cancel",
                    color = Color.Black
                )

            }
            Button(
                modifier = Modifier.weight(1f),
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(R.color.colorPrimary),
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text(
                    text = "Reschedule",
                    color = Color.White
                )
            }
        }
    }
}
