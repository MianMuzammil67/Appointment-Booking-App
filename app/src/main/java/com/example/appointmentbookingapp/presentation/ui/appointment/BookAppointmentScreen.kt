package com.example.appointmentbookingapp.presentation.ui.appointment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.appointmentbookingapp.R
import com.example.appointmentbookingapp.domain.model.Appointment
import com.example.appointmentbookingapp.domain.util.AppointmentStatusString
import com.example.appointmentbookingapp.presentation.state.UiState
import com.example.appointmentbookingapp.presentation.ui.components.BookingSuccessDialog
import com.example.appointmentbookingapp.presentation.ui.home.viewModel.SharedDoctorViewModel
import java.time.LocalDate
import java.time.YearMonth
import java.time.ZoneId
import java.util.Date
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookAppointmentScreen(
    navController: NavHostController,
    sharedDoctorViewModel: SharedDoctorViewModel = viewModel()
) {

    val viewModel: AppointmentViewModel = hiltViewModel()
    val firebaseDateState by viewModel.firebaseTimeFlow.collectAsState()
    val currentDoctor by sharedDoctorViewModel.selectedDoctor.collectAsState()
    val currentUser by viewModel.currentUserId.collectAsState()
    val notAvailableSlots by viewModel.notAvailableSlots.collectAsState()

    val bookingState by viewModel.bookingState.collectAsState()
    val isLoading = bookingState is UiState.Loading
    val isSuccess = bookingState is UiState.Success

    var showSuccessDialog by remember { mutableStateOf(false) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(LocalDate.now()) }

    var selectedTime by remember { mutableStateOf<String?>(null) }
    val utcDate = Date.from(selectedDate!!.atStartOfDay(ZoneId.of("UTC")).toInstant())

    val timeSlots = listOf(
        "09-10 AM", "10-11 AM", "11-12 AM",
        "12-01 PM", "03-04 PM", "04-05 PM",
    )

    LaunchedEffect(key1 = selectedDate) {
        selectedDate?.let {
            viewModel.getNotAvailableSlots(currentDoctor.id, utcDate)
        }
    }

    // Log when it changes
    LaunchedEffect(notAvailableSlots) {
        Log.d("appointmentScreen", "Updated slots: $notAvailableSlots")
    }

    LaunchedEffect(isSuccess) {
        if (isSuccess) {
            showSuccessDialog = true
        }
    }

    if (showSuccessDialog) {
        BookingSuccessDialog(
            onDismiss = {
                showSuccessDialog = false
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Book Appointment", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
        bottomBar = {
            Button(
                onClick = {
                    // Handle confirmation
                    val appointment = Appointment(
                        appointmentId = UUID.randomUUID().toString(),
                        patientId = currentUser ?: "currentUser is null",
                        appointmentDate = utcDate,
                        timeSlot = selectedTime ?: "selectedTime is null",
                        status = AppointmentStatusString.PENDING,
                        doctorId = currentDoctor.id
                    )
                    viewModel.bookAppointment(appointment)
//                  updating appointment date again to update ui in real time
                    appointment.appointmentDate?.let { date ->
                        viewModel.getNotAvailableSlots(appointment.doctorId, date)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .navigationBarsPadding(),
                shape = RoundedCornerShape(8.dp),
                enabled = selectedTime != null,

                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedTime != null) colorResource(R.color.colorPrimary) else Color.Gray,
                    disabledContainerColor = Color.Gray
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        color = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 8.dp),
                        strokeWidth = 2.dp
                    )
                    Text("Booking...", color = Color.White)
                } else
                    Text("Confirm", color = Color.White)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text("Select Date", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(8.dp))

            when (firebaseDateState) {
                is UiState.Loading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }

                is UiState.Error -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Failed to fetch server time.")
                    }
                }

                is UiState.Success -> {
                    val firebaseDate = (firebaseDateState as UiState.Success).data
                    // Initialize selectedDate only once
                    if (selectedDate == null) {
                        selectedDate = firebaseDate
                    }
                    AppointmentCalendar(
                        selectedDate = selectedDate!!,
                        onDateSelected = { selectedDate = it },
                        firebaseToday = firebaseDate
                    )
                    Log.d("appointmentScreen", firebaseDate.toString())
                }

                else -> {}
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Select Hour", fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(12.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                modifier = Modifier.height(150.dp),
                contentPadding = PaddingValues(4.dp)
            ) {
                items(timeSlots.size) { index ->
                    val time = timeSlots[index]
                    val isBooked = notAvailableSlots.contains(time)
                    val isSelected = selectedTime == time
                    Box(
                        modifier = Modifier
                            .padding(6.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                when {
                                    isBooked -> Color.Gray
                                    isSelected -> colorResource(R.color.colorPrimary)
                                    else -> Color(0xFFF1F5F9)
                                }
                            )
                            .clickable(enabled = !isBooked) {
                                selectedTime = if (isSelected) null else time
                            }
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (isBooked) "Booked" else time,
                            color = if (selectedTime == time) Color.White else Color.Black,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AppointmentCalendar(
    selectedDate: LocalDate = LocalDate.now(),
    onDateSelected: (LocalDate) -> Unit = {},
    firebaseToday: LocalDate

) {
    var currentMonth by remember { mutableStateOf(YearMonth.from(firebaseToday)) }
    val today = firebaseToday
    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")

    val firstDayOfMonth = currentMonth.atDay(1)
    val lastDayOfMonth = currentMonth.atEndOfMonth()
    val startDayOffset = firstDayOfMonth.dayOfWeek.value % 7
    val daysInMonth = lastDayOfMonth.dayOfMonth
    val totalCells = startDayOffset + daysInMonth
    val remainingCells = if (totalCells % 7 != 0) 7 - (totalCells % 7) else 0

    Column(
        modifier = Modifier
            .padding(16.dp)
            .background(
                Color.White,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        // Month navigation
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = {
                    val newMonth = currentMonth.minusMonths(1)
                    if (!newMonth.atEndOfMonth().isBefore(today)) {
                        currentMonth = newMonth
                    }
                },
                enabled = !currentMonth.atDay(1).isBefore(today.withDayOfMonth(1))
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    contentDescription = "Previous Month",
                    tint = Color.Black
                )
            }

            Text(
                text = "${
                    currentMonth.month.name.lowercase().replaceFirstChar { it.uppercase() }
                } ${currentMonth.year}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            IconButton(
                onClick = { currentMonth = currentMonth.plusMonths(1) }
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Next Month",
                    tint = Color.Black
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        // Weekday labels
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = Color.Black
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Dates grid
        val days = mutableListOf<LocalDate?>()
        repeat(startDayOffset) {
            days.add(null)
        }
        for (day in 1..daysInMonth) {
            days.add(currentMonth.atDay(day))
        }
        repeat(remainingCells) {
            days.add(null)
        }

        days.chunked(7).forEach { week ->
            Row(Modifier.fillMaxWidth()) {
                week.forEach { date ->
                    val isTodayOrFuture = date != null && !date.isBefore(today)
                    val isSelected = date == selectedDate

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isSelected -> colorResource(R.color.colorPrimary)
                                    else -> Color.Transparent
                                }
                            )
                            .clickable(
                                enabled = isTodayOrFuture,
                                onClick = { date?.let { onDateSelected(it) } }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = date?.dayOfMonth?.toString() ?: "",
                            color = when {
                                !isTodayOrFuture -> Color.Gray
                                isSelected -> Color.White
                                else -> Color.Black
                            },
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun BookAppointmentPreview() {
    BookAppointmentScreen(navController = rememberNavController())
}