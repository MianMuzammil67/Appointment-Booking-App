package com.example.appointmentbookingapp.domain.model

data class User(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val profileUrl: String = "",
    val role: String = "patient" // //Patient as Role is temporary for testing
)
