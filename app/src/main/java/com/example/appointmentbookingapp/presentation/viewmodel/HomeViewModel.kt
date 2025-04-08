package com.example.appointmentbookingapp.presentation.viewmodel

import com.example.appointmentbookingapp.domain.model.BannerItems
import com.example.appointmentbookingapp.domain.model.CategoryItem
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.presentation.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel {


    private val _bannerState = MutableStateFlow<UiState<List<BannerItems>>>(UiState.Loading)
    val bannerState: StateFlow<UiState<List<BannerItems>>> = _bannerState.asStateFlow()

    private val _categoriesState = MutableStateFlow<UiState<List<CategoryItem>>>(UiState.Loading)
    val categoriesState: StateFlow<UiState<List<CategoryItem>>> = _categoriesState.asStateFlow()

    private val _doctorState = MutableStateFlow<UiState<List<DoctorItem>>>(UiState.Loading)
    val doctorState: StateFlow<UiState<List<DoctorItem>>> = _doctorState.asStateFlow()



    private fun getBanners(){

    }

}