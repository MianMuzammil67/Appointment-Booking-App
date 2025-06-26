package com.example.appointmentbookingapp.domain.repository

import com.example.appointmentbookingapp.domain.model.Appointment
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.util.Resource
import java.time.LocalDate
import java.util.Date

interface AppointmentRepository {

    fun getCurrentUserId(): String
    suspend fun getFirebaseServerTime(): Resource<LocalDate>
    suspend fun bookAppointment(appointment: Appointment)
    suspend fun isTimeSlotAvailable(doctorId: String, date: LocalDate, time: String): Boolean
    suspend fun getNotAvailableSlots(doctorId: String, date: Date): List<String?>
    suspend fun getMyAppointments(): List<Appointment?>
    suspend fun getDoctorById(doctorId: String): DoctorItem?
}