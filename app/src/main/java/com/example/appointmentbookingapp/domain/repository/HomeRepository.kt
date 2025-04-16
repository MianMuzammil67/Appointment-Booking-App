package com.example.appointmentbookingapp.domain.repository

import com.example.appointmentbookingapp.domain.model.BannerItem
import com.example.appointmentbookingapp.domain.model.CategoryItem
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.util.Resource

interface HomeRepository {

    suspend fun getBanners(): Resource<List<BannerItem>>
    suspend fun getDoctorCategories(): Resource<List<CategoryItem>>
    suspend fun getDoctors(): Resource<List<DoctorItem>>
    fun getCurrentUserName(): String?
    fun getCurrentUserPhoto(): String?
}