package com.example.appointmentbookingapp.domain.repository

import com.example.appointmentbookingapp.util.Resource
import java.time.LocalDate

interface AppointmentRepository {

    suspend fun getFirebaseServerTime() : Resource<LocalDate>
}