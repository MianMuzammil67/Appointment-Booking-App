package com.example.appointmentbookingapp.util

object UserRole {
    const val PATIENT = "patient"
    const val DOCTOR = "doctor"
    const val NONE = ""  // used as default
    val VALID_ROLES = listOf(PATIENT, DOCTOR)
}
