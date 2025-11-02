package com.example.appointmentbookingapp.domain.model

import com.example.appointmentbookingapp.domain.util.AppointmentStatusString
import java.util.Date

data class Appointment(
    val appointmentId: String = "",
    val patientId: String = "",
    val doctorId: String = "",
    val appointmentDate: Date? = null,
    val timeSlot: String = "",
    val status: String = AppointmentStatusString.PENDING
)
