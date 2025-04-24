package com.example.appointmentbookingapp.presentation.ui.home.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appointmentbookingapp.domain.model.BannerItem
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.domain.model.DoctorCategory
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

    //    private val TAG = "HomeViewModel"
    private val logTag = HomeViewModel::class.java.simpleName

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    private val _profileImageUrl = MutableStateFlow<String?>(null)
    val profileImageUrl: StateFlow<String?> = _profileImageUrl

    private val _bannerFlow = MutableStateFlow<UiState<List<BannerItem>>>(UiState.Loading)
    val bannerFlow: StateFlow<UiState<List<BannerItem>>> = _bannerFlow.asStateFlow()

    private val _categoriesState = MutableStateFlow<UiState<List<DoctorCategory>>>(UiState.Loading)
    val categories: StateFlow<UiState<List<DoctorCategory>>> = _categoriesState.asStateFlow()

    private val _allCategoriesState = MutableStateFlow<UiState<List<DoctorCategory>>>(UiState.Loading)
    val allCategoriesState: StateFlow<UiState<List<DoctorCategory>>> =_allCategoriesState.asStateFlow()

    private val _doctorState = MutableStateFlow<UiState<List<DoctorItem>>>(UiState.Loading)
    val doctorState: StateFlow<UiState<List<DoctorItem>>> = _doctorState.asStateFlow()


    init {
        getCurrentUserInfo()
        getBanners()
        getSpecializationCategory()
        getAllSpecializationCategory()
        getDoctors()
    }

    private fun getCurrentUserInfo() {
        _userName.value = repository.getCurrentUserName()
        _profileImageUrl.value = repository.getCurrentUserPhoto()
    }

    private fun getBanners() = viewModelScope.launch {
        _bannerFlow.value = UiState.Loading
        when (val result = repository.getBanners()) {
            is Resource.Success -> {
                _bannerFlow.value = UiState.Success(result.data)
            }

            is Resource.Error -> {
                _bannerFlow.value = UiState.Error(result.message)
            }

            else -> {}


        }

    }

    private fun getAllSpecializationCategory() = viewModelScope.launch {
        _allCategoriesState.value = UiState.Loading

        when (val result = repository.getSpecializationCategory()) {
            is Resource.Success -> {
                _allCategoriesState.value = UiState.Success(result.data)
            }
            is Resource.Error -> {
                _allCategoriesState.value = UiState.Error(result.message)
            }
            else -> {}
        }
    }

    private fun getSpecializationCategory() = viewModelScope.launch {
        _categoriesState.value = UiState.Loading
        when (val result = repository.getSpecializationCategory()) {
            is Resource.Success -> {
                val categories = result.data.take(4)
                _categoriesState.value = UiState.Success(categories)
            }

            is Resource.Error -> {
                _categoriesState.value = UiState.Error(result.message)
            }
            else -> {}
        }
    }

    private fun getDoctors() = viewModelScope.launch {
        _doctorState.value = UiState.Loading
        when (val result = repository.getDoctors()) {
            is Resource.Success -> _doctorState.value = UiState.Success(result.data)
            is Resource.Error -> _doctorState.value = UiState.Error(result.message)
            else -> {}
        }
    }


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