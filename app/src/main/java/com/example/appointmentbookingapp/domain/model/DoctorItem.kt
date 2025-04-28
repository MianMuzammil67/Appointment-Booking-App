package com.example.appointmentbookingapp.domain.model

data class DoctorItem(
    val id: String = "",
    val name: String = "",
    val aboutDoctor: String = "",
    val imageUrl: String = "",
    val rating: String = "",
    val docCategory: String = "",
    var isFavorite: Boolean = false,

    val experienceYears: Int? = 0,
    val consultationFee: String = "",
//    val isOnlineAvailable: Boolean?,
    val languagesSpoken: List<String>? = emptyList(),
    val gender: String? = "",
    val reviewsCount: Int? = 0,
    )