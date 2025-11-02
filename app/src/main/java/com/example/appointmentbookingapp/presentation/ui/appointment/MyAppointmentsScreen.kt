package com.example.appointmentbookingapp.presentation.ui.appointment

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.appointmentbookingapp.domain.model.Appointment
import com.example.appointmentbookingapp.domain.model.AppointmentWithDoctor
import com.example.appointmentbookingapp.domain.model.AppointmentWithPatient
import com.example.appointmentbookingapp.domain.util.AppointmentStatusString
import com.example.appointmentbookingapp.presentation.state.UiState
import com.example.appointmentbookingapp.presentation.ui.components.CancelBookingDialog
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.AppointmentSharedViewModel
import com.example.appointmentbookingapp.presentation.ui.sharedviewmodel.UserRoleSharedViewModel
import com.example.appointmentbookingapp.util.UserRole
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()

    Log.d("MyAppointments", "userRole: $userRole")
//    var appointmentToCancel by remember { mutableStateOf<AppointmentWithDoctor?>(null) }
    var appointmentToCancel by remember { mutableStateOf<Any?>(null) }
    val tabItem = listOf("Upcoming", "Completed", "Cancelled")

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
                appointmentViewModel.cancelAppointment(appointmentItem as Appointment)
                appointmentToCancel = null
            }
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("My Appointments", fontWeight = FontWeight.Bold) }
            )
        },
    ) { innerPadding ->
        Box(
            Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 8.dp),

            ) {

            val pagerState = rememberPagerState(initialPage = 0) { tabItem.size }

            Column {
                TabRow(selectedTabIndex = pagerState.currentPage) {
                    tabItem.forEachIndexed { index, title ->
                        Tab(
                            text = {
                                Text(
                                    title, color = if (pagerState.currentPage == index) {
                                        MaterialTheme.colorScheme.onSurface
                                    } else {
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                    }
                                )
                            },
                            selected = pagerState.currentPage == index,
                            onClick = {
                                scope.launch {
                                    pagerState.animateScrollToPage(index)
                                }
                            }
                        )
                    }
                }
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) { page ->

                    val status = when (page) {
                        0 -> AppointmentStatusString.PENDING
                        1 -> AppointmentStatusString.COMPLETED
                        2 -> AppointmentStatusString.CANCELLED
                        else -> AppointmentStatusString.PENDING
                    }

                    val uiState = if (userRole == UserRole.PATIENT) {
                        appointmentsForPatientState.filterByStatusOrSelf { it is AppointmentWithDoctor && it.appointment.status == status }

                    } else {
                        appointmentsForDoctorState.filterByStatusOrSelf { it is AppointmentWithPatient && it.appointment.status == status }
                    }
                    AppointmentList(
                        uiState = uiState,
                        userRole = userRole,
                        onCancel = { appointmentToCancel = it },
                        onItemClick = { item ->

                            if (userRole == UserRole.PATIENT && item is AppointmentWithDoctor && item.appointment.status == AppointmentStatusString.PENDING && item.appointment.status != AppointmentStatusString.CANCELLED) {
                                appointmentSharedViewModel.setAppointment(item.appointment)
                                navController.navigate("WaitingRoomScreen")
                            } else if (userRole == UserRole.DOCTOR && item is AppointmentWithPatient && item.appointment.status != AppointmentStatusString.CANCELLED && item.appointment.status != AppointmentStatusString.COMPLETED) {
                                appointmentSharedViewModel.setAppointment(item.appointment)
                                navController.navigate("CallScreen")
                            }
                        }
                    )

                }
            }

        }

    }
}

@Composable
fun AppointmentList(
    uiState: UiState<List<Any?>>,
    userRole: String,
    onCancel: (Any) -> Unit,
    onItemClick: (Any) -> Unit
) {
    when (uiState) {
        is UiState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is UiState.Success -> {
            val appointments = uiState.data
            if (appointments.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No appointments found.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                ) {
                    items(appointments) { item ->
                        Spacer(modifier = Modifier.height(8.dp))
                        item?.let {
                            when {
                                userRole == UserRole.PATIENT && it is AppointmentWithDoctor -> {
                                    AppointmentItem(
                                        appointment = it.appointment,
                                        doctorItem = it.doctor,
                                        onClick = { onItemClick(it) },
                                        onCancelClick = { onCancel(it) },
                                        onRescheduleClick = {},
                                        user = null
                                    )
                                }

                                userRole == UserRole.DOCTOR && it is AppointmentWithPatient -> {
                                    AppointmentItem(
                                        appointment = it.appointment,
                                        doctorItem = null,
                                        user = it.patient,
                                        onClick = { onItemClick(it) },
                                        onCancelClick = { onCancel(it) },
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Failed to load appointments: ${uiState.message}", color = Color.Red)
            }
        }

        else -> {}
    }
}


fun <T> UiState<List<T>>.filterByStatusOrSelf(filter: (T) -> Boolean): UiState<List<T>> {
    return when (this) {
        is UiState.Success -> UiState.Success(this.data.filter(filter))
        else -> this
    }
}


@Preview
@Composable
fun MyAppointmentsPreview() {
//    MyAppointments(navController = rememberNavController())
}



