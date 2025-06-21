package com.example.appointmentbookingapp.presentation.ui.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.model.BannerItem
import com.example.appointmentbookingapp.domain.model.DoctorCategory
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.domain.repository.HomeRepository
import com.example.appointmentbookingapp.presentation.state.UiState
import com.example.appointmentbookingapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : ViewModel() {

    private val logTag = "HomeViewModel"

    private val _bannerFlow = MutableStateFlow<UiState<List<BannerItem>>>(UiState.Loading)
    val bannerFlow: StateFlow<UiState<List<BannerItem>>> = _bannerFlow.asStateFlow()

    private val _categoriesState = MutableStateFlow<UiState<List<DoctorCategory>>>(UiState.Loading)
    val categories: StateFlow<UiState<List<DoctorCategory>>> = _categoriesState.asStateFlow()

    private val _allCategoriesState =
        MutableStateFlow<UiState<List<DoctorCategory>>>(UiState.Loading)
    val allCategoriesState: StateFlow<UiState<List<DoctorCategory>>> =
        _allCategoriesState.asStateFlow()

    private val _allDoctorState = MutableStateFlow<UiState<List<DoctorItem>>>(UiState.Loading)
    val allDoctorState: StateFlow<UiState<List<DoctorItem>>> = _allDoctorState.asStateFlow()

    private val _topDoctorState = MutableStateFlow<UiState<List<DoctorItem>>>(UiState.Loading)
    val topDoctorState: StateFlow<UiState<List<DoctorItem>>> = _topDoctorState.asStateFlow()


    init {
        getBanners()
        getAllSpecializationCategory()
        getDoctors()
    }

    private fun getBanners() = viewModelScope.launch {
        _bannerFlow.value = UiState.Loading
        when (val result = repository.getBanners()) {
            is Resource.Success -> {
                _bannerFlow.value = UiState.Success(result.data)
            }

            is Resource.Error -> {
                _bannerFlow.value = UiState.Error(result.message)
                Log.d(logTag, "getBanners: ${result.message}")
            }

            else -> {}
        }
    }

    private var getAllCategoryCallCount = 0

    private fun getAllSpecializationCategory() = viewModelScope.launch {
        getAllCategoryCallCount++
        Log.d(logTag, "getAllSpecializationCategory called: $getAllCategoryCallCount times")

        _categoriesState.value = UiState.Loading
        _allCategoriesState.value = UiState.Loading

        when (val result = repository.getSpecializationCategory()) {
            is Resource.Success -> {
                val allCategories = result.data
                val topCategories = allCategories.take(4)
                _categoriesState.value = UiState.Success(topCategories)
                _allCategoriesState.value = UiState.Success(allCategories)
            }

            is Resource.Error -> {
                _allCategoriesState.value = UiState.Error(result.message)
                _categoriesState.value = UiState.Error(result.message)

                Log.d(logTag, "getBanners: ${result.message}")

            }

            else -> {}
        }
    }

    private var getAllDoctorsCallCount = 0

    private fun getDoctors() = viewModelScope.launch {
        getAllDoctorsCallCount++
        Log.d(logTag, "getDoctors called: $getAllDoctorsCallCount times")

        _topDoctorState.value = UiState.Loading
        _allDoctorState.value = UiState.Loading

        when (val result = repository.getDoctors()) {
            is Resource.Success -> {
                val allDoctors = result.data
                val topDoctors = allDoctors.sortedByDescending { it.rating }.take(4)
                _allDoctorState.value = UiState.Success(allDoctors)
                _topDoctorState.value = UiState.Success(topDoctors)
            }

            is Resource.Error -> {
                _allDoctorState.value = UiState.Error(result.message)
                _topDoctorState.value = UiState.Error(result.message)
            }

            else -> {}
        }
    }

}