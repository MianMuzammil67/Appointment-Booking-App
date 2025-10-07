package com.example.appointmentbookingapp.domain.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val profileUrl: String = "",
    val role: String = "patient"
)
