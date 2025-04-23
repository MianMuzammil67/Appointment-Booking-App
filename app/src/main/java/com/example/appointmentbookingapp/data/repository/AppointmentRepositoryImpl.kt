package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.AppointmentRemoteDataSource
import com.example.appointmentbookingapp.domain.model.Appointment
import com.example.appointmentbookingapp.domain.repository.AppointmentRepository
import com.example.appointmentbookingapp.util.Resource
import java.time.LocalDate

class AppointmentRepositoryImpl(
    private val remote: AppointmentRemoteDataSource
) : AppointmentRepository {

    override fun getCurrentUserId(): String {
        return remote.getCurrentUserId()
    }

    override suspend fun getFirebaseServerTime(): Resource<LocalDate> {
        return try {
            val data = remote.getFirebaseServerTime()
            Resource.Success(data)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun bookAppointment(appointment: Appointment) {
        try {
            remote.bookAppointment(appointment)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }

    }
}