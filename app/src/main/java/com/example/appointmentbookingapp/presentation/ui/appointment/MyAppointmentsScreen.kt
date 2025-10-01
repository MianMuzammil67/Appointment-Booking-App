package com.example.appointmentbookingapp.presentation.ui.appointment

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appointmentbookingapp.domain.model.AppointmentWithDoctor
import com.example.appointmentbookingapp.domain.model.AppointmentWithPatient
import com.example.appointmentbookingapp.presentation.state.UiState
import com.example.appointmentbookingapp.presentation.ui.components.CancelBookingDialog
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.AppointmentSharedViewModel
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.UserRoleSharedViewModel
import com.example.appointmentbookingapp.util.UserRole

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppointments(
    navController: NavController,
    appointmentViewModel: AppointmentViewModel,
    appointmentSharedViewModel: AppointmentSharedViewModel,
    userRoleSharedViewModel: UserRoleSharedViewModel
) {
    val context = LocalContext.current

    val appointmentsForPatientState by appointmentViewModel.appointmentsForPatient.collectAsState()
    val appointmentsForDoctorState by appointmentViewModel.appointmentsForDoctor.collectAsState()
    val bookingCancelState by appointmentViewModel.bookingCancelState.collectAsState()
    val userRole by userRoleSharedViewModel.userRole.collectAsState()

    Log.d("MyAppointments", "userRole: $userRole")
    var appointmentToCancel by remember { mutableStateOf<AppointmentWithDoctor?>(null) }

    LaunchedEffect(bookingCancelState) {
        when (bookingCancelState) {
            is UiState.Loading -> {}
            is UiState.Success -> {
                if (userRole == UserRole.PATIENT) {
                    appointmentViewModel.getAppointments()
                } else {
                    appointmentViewModel.getAppointmentsForDoctor()
                }

                appointmentToCancel = null
                Toast.makeText(context, "Booking canceled", Toast.LENGTH_SHORT).show()
            }

            is UiState.Error -> {
                val errorMessage = (bookingCancelState as UiState.Error).message
                Toast.makeText(
                    context,
                    "Failed to cancel booking $errorMessage",
                    Toast.LENGTH_SHORT
                ).show()
            }

            else -> {}
        }

    }

    LaunchedEffect(Unit) {

        if (userRole == UserRole.PATIENT) {
            appointmentViewModel.getAppointments()
        } else {
            appointmentViewModel.getAppointmentsForDoctor()
        }
    }
    appointmentToCancel?.let { appointmentItem ->
        CancelBookingDialog(
            onDismiss = { appointmentToCancel = null },
            onCancelBooking = {
                appointmentViewModel.cancelAppointment(appointmentItem.appointment)
                appointmentToCancel = null
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("My Appointments", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },

        ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(8.dp),

            ) {

            val uiState =
                if (userRole == UserRole.PATIENT) appointmentsForPatientState else appointmentsForDoctorState

            when (uiState) {
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Success -> {
                    val appointment =
                        (myAppointmentsState as UiState.Success<List<AppointmentWithDoctor?>>).data
                    if (appointment.isEmpty()) {
                    val appointments = uiState.data
                    if (appointments.isEmpty()) {
                        Text(
                            "No appointments found.",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    } else {
                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 8.dp)
                        )
                        {
                            items(appointments) { item ->
                                Spacer(modifier = Modifier.height(8.dp))
                                item?.let {
                                    AppointmentItem(
                                        it.appointment,
                                        item.doctor!!,
                                        onClick = {},
                                        onCancelClick = {
                                            appointmentToCancel = item
                                        },
                                        onRescheduleClick = {}
                                    )
                                    when {
                                        userRole == UserRole.PATIENT && it is AppointmentWithDoctor -> {
                                            AppointmentItem(
                                                appointment = it.appointment,
                                                doctorItem = it.doctor,
                                                onClick = {
                                                    appointmentSharedViewModel.setAppointmentId(it.appointment.appointmentId)
                                                    navController.navigate("WaitingRoomScreen")
                                                },
                                                onCancelClick = { appointmentToCancel = it },
                                                onRescheduleClick = {},
                                                user = null
                                            )
                                        }
                                        userRole == UserRole.DOCTOR && it is AppointmentWithPatient -> {
                                            AppointmentItem(
                                                appointment = it.appointment,
                                                doctorItem = null,
                                                user = it.patient,
                                                onClick = {
                                                    appointmentSharedViewModel.setAppointmentId(it.appointment.appointmentId)
                                                    navController.navigate("CallScreen")
                                                },
                                                onCancelClick = {},
//                                                onCancelClick = { appointmentToCancel = it },
                                                onRescheduleClick = {}
                                            )
                                        }
                                    }


                                }
                            }
                        }
                    }
                }

                is UiState.Error -> {
                    Text("Failed to load appointments", color = Color.Red)
                }

                else -> {}

            }

        }

    }
}

@Preview
@Composable
fun MyAppointmentsPreview() {
//    MyAppointments(navController = rememberNavController())
}



