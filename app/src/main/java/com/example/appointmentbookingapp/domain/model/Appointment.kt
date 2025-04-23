package com.example.appointmentbookingapp.domain.model

import java.time.LocalDate

data class Appointment(
    val appointmentId: String = "",
    val patientId: String = "",
    val doctorId: String = "",
    val appointmentDate: LocalDate? = null,
    val timeSlot: String = "",
    val status: String = "pending"
)
