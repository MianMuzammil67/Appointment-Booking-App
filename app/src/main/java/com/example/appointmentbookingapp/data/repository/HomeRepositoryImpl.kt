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

    override fun getCurrentUserName(): String? {
        return remote.getCurrentUserName()
    }

    override fun getCurrentUserPhoto(): String {
        return remote.getCurrentUserPhoto()
    }

    //    private val _userName = MutableStateFlow<String?>(null)
//    val userName: StateFlow<String?> = _userName
//
//    private val _profileImageUrl = MutableStateFlow<String?>(null)
//    val profileImageUrl: StateFlow<String?> = _profileImageUrl
//
//    private val _bannerFlow = MutableStateFlow<UiState<List<BannerItems>>>(UiState.Loading)
//    val bannerFlow: StateFlow<UiState<List<BannerItems>>> = _bannerFlow.asStateFlow()
//
//    private val _categories = MutableStateFlow<UiState<List<CategoryItem>>>(UiState.Loading)
//    val categories: StateFlow<UiState<List<CategoryItem>>> = _categories.asStateFlow()
//
//    private val _doctorState = MutableStateFlow<UiState<List<DoctorItem>>>(UiState.Loading)
//    val doctorState: StateFlow<UiState<List<DoctorItem>>> = _doctorState.asStateFlow()
//
//
//    override fun getCurrentUserInfo() {
//        val currentUser = firebaseAuth.currentUser
//        _userName.value = currentUser?.displayName
//        _profileImageUrl.value = currentUser?.photoUrl?.toString()
//    }
//
//    override suspend fun getBanners() {
//        try {
//            _bannerFlow.emit(UiState.Loading)
//            val snapshot = firestore.collection("Banners")
//                .get().await()
//            val banner = snapshot.documents.mapNotNull { document ->
//                document.toObject(BannerItems::class.java)
//            }
//            _bannerFlow.emit(UiState.Success(banner))
//        } catch (e: Exception) {
//            _bannerFlow.emit(UiState.Error(e.message.toString()))
//        }
//    }
//
//    override suspend fun getDoctorCategories() {
//        try {
//            _categories.emit(UiState.Loading)
//            val snapshot = firestore.collection("DoctorCategories")
//                .get().await()
//            val categories = snapshot.documents.mapNotNull { documentSnapshot ->
//                documentSnapshot.toObject(CategoryItem::class.java)
//            }
//            _categories.emit(UiState.Success(categories))
//        } catch (e: Exception) {
//            _categories.emit(UiState.Error(e.message.toString()))
//        }
//
//    }
//
//    override suspend fun getDoctors() {
//        try {
//            _doctorState.emit(UiState.Loading)
//            val snapshot = firestore.collection("doctors")
//                .get().await()
//
//           val doctors = snapshot.documents.mapNotNull { documentSnapshot ->
//                documentSnapshot.toObject(DoctorItem::class.java)
//            }
//            _doctorState.emit(UiState.Success(doctors))
//
//        } catch (e: Exception) {
//            _doctorState.emit(UiState.Error(e.message.toString()))
//
//        }
//    }


}