package com.example.appointmentbookingapp.domain.model

data class AppointmentWithDoctor(
    val appointment: Appointment,
    val doctor: DoctorItem?
)