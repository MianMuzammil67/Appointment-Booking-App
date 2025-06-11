package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.FavoritesRemoteDataSource
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.domain.repository.FavoriteRepository

class FavoriteRepositoryImpl(
    private val favoriteRemoteDataSource: FavoritesRemoteDataSource
): FavoriteRepository {

    override suspend fun addToFavorites(doctorItem: DoctorItem) {
        favoriteRemoteDataSource.addToFavorites(doctorItem)
    }

    override suspend fun removeFromFavorites(doctorItem: DoctorItem) {
        favoriteRemoteDataSource.removeFromFavorites(doctorItem)
    }

    override suspend fun getFavorites(): List<DoctorItem> {
        return favoriteRemoteDataSource.getFavorites()
    }

    override suspend fun isDoctorFavorite(doctorId: String): Boolean {
        return favoriteRemoteDataSource.isDoctorFavorite(doctorId)
    }



}