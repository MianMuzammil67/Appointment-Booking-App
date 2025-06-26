package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.AppointmentRemoteDataSource
import com.example.appointmentbookingapp.domain.model.Appointment
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.domain.repository.AppointmentRepository
import com.example.appointmentbookingapp.util.Resource
import java.time.LocalDate
import java.util.Date

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

    override suspend fun isTimeSlotAvailable(
        doctorId: String,
        date: LocalDate,
        time: String
    ): Boolean {
        return remote.isTimeSlotAvailable(doctorId, date, time)
    }

    override suspend fun getNotAvailableSlots(doctorId: String, date: Date): List<String?> {
        return remote.getNotAvailableSlots(doctorId,date)
    }

    override suspend fun getMyAppointments(): List<Appointment?> {
       return remote.getMyAppointments()
    }

    override suspend fun getDoctorById(doctorId: String): DoctorItem? {
        return remote.getDoctorById(doctorId)
    }
}