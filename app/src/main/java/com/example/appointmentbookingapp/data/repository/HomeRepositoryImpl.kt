package com.example.appointmentbookingapp.data.repository

import com.example.appointmentbookingapp.data.remorte.HomeRemoteDataSource
import com.example.appointmentbookingapp.domain.model.BannerItem
import com.example.appointmentbookingapp.domain.model.DoctorCategory
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.domain.repository.HomeRepository
import com.example.appointmentbookingapp.util.Resource
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val remote: HomeRemoteDataSource
) : HomeRepository {

    override suspend fun getBanners(): Resource<List<BannerItem>> {
        return try {
            val banner = remote.getBanners()
            Resource.Success(banner)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun getSpecializationCategory(): Resource<List<DoctorCategory>> {
        return try {
            val categories = remote.getSpecializationCategory()

            Resource.Success(categories)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun getDoctors(): Resource<List<DoctorItem>> {
        return try {
            val doctors = remote.getDoctors()
            Resource.Success(doctors)
        } catch (e: Exception) {
            Resource.Error(e.message.toString())
        }
    }

}