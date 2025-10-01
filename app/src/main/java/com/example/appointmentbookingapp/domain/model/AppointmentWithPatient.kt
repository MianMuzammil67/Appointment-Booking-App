package com.example.appointmentbookingapp.domain.model

data class AppointmentWithPatient (
    val appointment: Appointment,
    val patient: User?
)