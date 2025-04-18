package com.example.appointmentbookingapp.domain.repository

import com.example.appointmentbookingapp.domain.model.BannerItem
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.domain.model.DoctorCategory
import com.example.appointmentbookingapp.util.Resource

interface HomeRepository {

    suspend fun getBanners(): Resource<List<BannerItem>>
    suspend fun getSpecializationCategory(): Resource<List<DoctorCategory>>
    suspend fun getDoctors(): Resource<List<DoctorItem>>
    fun getCurrentUserName(): String?
    fun getCurrentUserPhoto(): String?
}