package com.example.appointmentbookingapp.domain.repository

import com.example.appointmentbookingapp.domain.model.DoctorItem

interface FavoriteRepository {
    suspend fun addToFavorites(doctorItem: DoctorItem)
    suspend fun removeFromFavorites(doctorItem: DoctorItem)
    suspend fun getFavorites(): List<DoctorItem>
    suspend fun isDoctorFavorite(doctorId: String): Boolean
}