package com.example.appointmentbookingapp.domain.model

data class DoctorItem(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val imageUrl: String = "",
    val rating: String = "",
    val docCategory:String= "",
    val isFavorite: Boolean = false,

)