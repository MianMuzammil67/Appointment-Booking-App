package com.example.appointmentbookingapp.presentation.ui.home

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

    private val _categories = MutableStateFlow<UiState<List<CategoryItem>>>(UiState.Loading)
    val categories: StateFlow<UiState<List<CategoryItem>>> = _categories.asStateFlow()

    private val _doctorState = MutableStateFlow<UiState<List<DoctorItem>>>(UiState.Loading)
    val doctorState: StateFlow<UiState<List<DoctorItem>>> = _doctorState.asStateFlow()


//        private val _categories = mutableStateOf<List<CategoryItem>>(emptyList())
//        val categories: State<List<CategoryData>> = _categories
//
//        private val _doctorList = mutableStateOf<List<DoctorItem>>(emptyList())
//        val doctorList: State<List<DoctorItem>> = _doctorList
//
//        private val _userName = mutableStateOf("Mian Muzammil")
//        val userName: State<String> = _userName
//
//        private val _search = mutableStateOf("")
//        val search: State<String> = _search
//
//        fun onSearchChange(newValue: String) {
//            _search.value = newValue
//        }
//
//        init {
//            loadData()
//        }
//
//        private fun loadData() {
//            // Simulate loading
//            _categories.value = TempDatabase.getCategories()
//            _doctorList.value = TempDatabase.getDoctors()
//        }
//    }


}