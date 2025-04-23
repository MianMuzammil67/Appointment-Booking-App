package com.example.appointmentbookingapp.domain.repository

import com.example.appointmentbookingapp.domain.model.Appointment
import com.example.appointmentbookingapp.util.Resource
import java.time.LocalDate

interface AppointmentRepository {

    fun getCurrentUserId(): String
    suspend fun getFirebaseServerTime(): Resource<LocalDate>
    suspend fun bookAppointment(appointment: Appointment)
}