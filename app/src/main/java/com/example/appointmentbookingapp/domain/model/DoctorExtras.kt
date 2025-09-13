package com.example.appointmentbookingapp.domain.model

data class DoctorExtras(
    val aboutDoctor: String = "",
    val docCategory: String = "",
    val experienceYears: Int = 0,
    val consultationFee: String = "",
    val rating: String = "",
    val profileUrl: String = "",
    val reviewsCount: Int? = 0,
    val languagesSpoken: List<String>? = emptyList(),
    val gender: String = ""
)
