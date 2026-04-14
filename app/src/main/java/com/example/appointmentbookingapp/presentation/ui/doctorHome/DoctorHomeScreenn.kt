package com.example.appointmentbookingapp.presentation.ui.doctorHome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.presentation.ui.home.HomeHeaderSection
import com.example.appointmentbookingapp.presentation.ui.profile.ProfileViewModel

// 1. Data Models (unchanged)
//data class Appointment(
//    val id: String,
//    val patientName: String,
//    val time: String,
//    val reason: String,
//    val status: AppointmentStatus
//)

//enum class AppointmentStatus {
//    PENDING,
//    CONFIRMED,
//    COMPLETED,
//    CANCELED
//}

// 2. The Main Doctor Home Screen Composable with temporary data
@Composable
fun DoctorHomeScreenn(
    navController: NavController,
    profileViewModel: ProfileViewModel = hiltViewModel()
) {
    val doctorName by profileViewModel.userName.collectAsState()
    val profileImageUrl by profileViewModel.photoUrl.collectAsState()

    // --- TEMPORARY DATA ---
    val pendingAppointments = remember {
        mutableStateListOf(
            Appointment(
                id = "req1",
                patientName = "Jane Doe",
                time = "10:00 AM",
                reason = "Initial consultation",
                status = AppointmentStatus.PENDING
            ),
            Appointment(
                id = "req2",
                patientName = "John Smith",
                time = "11:30 AM",
                reason = "Follow-up for lab results",
                status = AppointmentStatus.PENDING
            )
        )
    }

    val upcomingAppointments = remember {
        mutableStateListOf(
            Appointment(
                id = "app1",
                patientName = "Michael Brown",
                time = "1:00 PM",
                reason = "Check-up",
                status = AppointmentStatus.CONFIRMED
            ),
            Appointment(
                id = "app2",
                patientName = "Sarah Lee",
                time = "2:30 PM",
                reason = "Medication review",
                status = AppointmentStatus.CONFIRMED
            )
        )
    }

    // --- END TEMPORARY DATA ---

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            HomeHeaderSection(
                userName = doctorName ?:"Dr. Emily",
                profileUrl = profileImageUrl ?:"https://ca1-eno.edcdn.com/team/_c240x240/michael-walsh-v2.jpg?mtime=20170712105122"
            )
            Spacer(modifier = Modifier.height(24.dp))
            QuickActionsSection(
                navController = navController,
                pendingCount = pendingAppointments.size
            )
            Spacer(modifier = Modifier.height(24.dp))
            PendingAppointmentsSectionn(
                navController = navController,
                pendingRequests = pendingAppointments
            )
            Spacer(modifier = Modifier.height(24.dp))
            UpcomingAppointmentsSectionn(
                navController = navController,
                upcomingAppointments = upcomingAppointments
            )
        }
    }
}

// 3. Individual Composable Components (unchanged from the previous version)

@Composable
fun DoctorHeaderSectionn(doctorName: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Welcome back,",
            style = MaterialTheme.typography.titleMedium,
            color = colorScheme.onSurfaceVariant
        )
        Text(
            text = doctorName,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = colorScheme.onSurface
        )
    }
}

@Composable
fun QuickActionsSection(navController: NavController, pendingCount: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ElevatedCard(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.elevatedCardColors(containerColor = colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Icon(
                    imageVector = Icons.Default.ChatBubble,
                    contentDescription = "Messages",
                    tint = colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "2 New Messages",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        ElevatedCard(
            modifier = Modifier.weight(1f),
            colors = CardDefaults.elevatedCardColors(containerColor = colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForwardIos,
                    contentDescription = "Pending Appointments",
                    tint = colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$pendingCount Pending",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun PendingAppointmentsSectionn(navController: NavController, pendingRequests: List<Appointment>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Pending Approvals",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (pendingRequests.isEmpty()) {
            Text(
                "No new appointment requests.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            pendingRequests.take(3).forEach { request ->
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    AppointmentRequestCardd(
                        request = request,
                        onApprove = { /* Logic */ },
                        onDecline = { /* Logic */ }
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun AppointmentRequestCardd(request: Appointment, onApprove: () -> Unit, onDecline: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = request.patientName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "(${request.reason})",
                    style = MaterialTheme.typography.bodySmall,
                    color = colorScheme.onSurfaceVariant
                )
            }
            Text(text = "Time: ${request.time}", color = colorScheme.onSurfaceVariant)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(
                    onClick = onApprove,
                    colors = buttonColors(containerColor = colorResource(R.color.colorPrimary))
                ) {
                    Text("Approve")
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(
                    onClick = onDecline,
                    colors = buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = colorResource(R.color.colorPrimary)
                    )
                ) {
                    Text("Decline")
                }
            }
        }
    }
}

@Composable
fun UpcomingAppointmentsSectionn(
    navController: NavController,
    upcomingAppointments: List<Appointment>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Today's Appointments",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))

        if (upcomingAppointments.isEmpty()) {
            Text(
                "No confirmed appointments for today.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        } else {
            upcomingAppointments.forEach { appointment ->
                UpcomingAppointmentCardd(appointment = appointment)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun UpcomingAppointmentCardd(appointment: Appointment) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = appointment.patientName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(text = appointment.reason, color = colorScheme.onSurfaceVariant)
            }
            Text(
                text = appointment.time,
                style = MaterialTheme.typography.titleMedium,
                color = colorResource(R.color.colorPrimary)
            )
        }
    }
}

// 4. Preview
@Preview(showBackground = true)
@Composable
fun DoctorHomeScreennPreview() {
    MaterialTheme {
        DoctorHomeScreenn(navController = rememberNavController())
    }
}