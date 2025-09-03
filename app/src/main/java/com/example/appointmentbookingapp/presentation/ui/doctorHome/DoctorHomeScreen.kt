package com.example.appointmentbookingapp.presentation.ui.doctorHome
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

data class Appointment(
    val id: String,
    val patientName: String,
    val time: String,
    val reason: String,
    val status: AppointmentStatus
)

enum class AppointmentStatus {
    PENDING,
    CONFIRMED,
    COMPLETED,
    CANCELED
}

@Composable
fun DoctorHomeScreen(navController: NavController) {
    val doctorName = "Dr. Emily Carter"
    val pendingAppointments = remember { mutableStateListOf<Appointment>() }
    val upcomingAppointments = remember { mutableStateListOf<Appointment>() }


    LaunchedEffect(Unit) {
        // Simulating data fetching
        pendingAppointments.addAll(
            listOf(
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
        )
        upcomingAppointments.addAll(
            listOf(
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
        )
    }

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
            Spacer(modifier = Modifier.height(16.dp))

            // Header Section
            DoctorHeaderSection(doctorName = doctorName)
            Spacer(modifier = Modifier.height(24.dp))

            // Quick Actions Section (e.g., Unread Messages)
            QuickActionsSection(navController = navController)
            Spacer(modifier = Modifier.height(24.dp))

            // Pending Appointments Section (HIGH PRIORITY)
            PendingAppointmentsSection(
                navController = navController,
                pendingRequests = pendingAppointments
            )
            Spacer(modifier = Modifier.height(24.dp))

            // Upcoming Appointments Section
            UpcomingAppointmentsSection(
                navController = navController,
                upcomingAppointments = upcomingAppointments
            )
        }
    }
}

// 3. Individual Composable Components

@Composable
fun DoctorHeaderSection(doctorName: String) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Welcome back,",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = doctorName,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun QuickActionsSection(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.ChatBubble,
                    contentDescription = "Messages",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "You have 2 new messages",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
fun PendingAppointmentsSection(navController: NavController, pendingRequests: List<Appointment>) {
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
                AppointmentRequestCard(
                    request = request,
                    onApprove = { /* TODO: Implement approval logic */ },
                    onDecline = { /* TODO: Implement decline logic */ }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (pendingRequests.size > 3) {
                Text(
                    text = "See All (${pendingRequests.size} total)",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable { /* Navigate to a dedicated pending appointments screen */ }
                )
            }
        }
    }
}

@Composable
fun AppointmentRequestCard(request: Appointment, onApprove: () -> Unit, onDecline: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = request.patientName, style = MaterialTheme.typography.titleMedium)
            Text(text = "Time: ${request.time}", color = Color.Gray)
            Text(text = "Reason: ${request.reason}", color = Color.Gray)
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Button(onClick = onApprove) {
                    Text("Approve")
                }
                Spacer(modifier = Modifier.width(8.dp))
                OutlinedButton(onClick = onDecline) {
                    Text("Decline")
                }
            }
        }
    }
}

@Composable
fun UpcomingAppointmentsSection(navController: NavController, upcomingAppointments: List<Appointment>) {
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
                UpcomingAppointmentCard(appointment = appointment)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun UpcomingAppointmentCard(appointment: Appointment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = appointment.patientName, style = MaterialTheme.typography.titleMedium)
                Text(text = appointment.reason, color = Color.Gray)
            }
            Text(text = appointment.time, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DoctorHomeScreenPreview() {

    DoctorHomeScreen(navController = rememberNavController())
}