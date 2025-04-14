package com.example.appointmentbookingapp.presentation.ui.home

import androidx.lifecycle.ViewModel
import com.example.appointmentbookingapp.domain.model.BannerItems
import com.example.appointmentbookingapp.domain.model.CategoryItem
import com.example.appointmentbookingapp.domain.model.DoctorItem
import com.example.appointmentbookingapp.presentation.state.UiState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) : ViewModel() {

    //    private val TAG = "HomeViewModel"
    private val logTag = HomeViewModel::class.java.simpleName

    private val _userName = MutableStateFlow<String?>(null)
    val userName: StateFlow<String?> = _userName

    private val _profileImageUrl = MutableStateFlow<String?>(null)
    val profileImageUrl: StateFlow<String?> = _profileImageUrl

    private val _bannerState = MutableStateFlow<UiState<List<BannerItems>>>(UiState.Loading)
    val bannerState: StateFlow<UiState<List<BannerItems>>> = _bannerState.asStateFlow()

    private val _categories = MutableStateFlow<UiState<List<CategoryItem>>>(UiState.Loading)
    val categories: StateFlow<UiState<List<CategoryItem>>> = _categories.asStateFlow()

    private val _doctorState = MutableStateFlow<UiState<List<DoctorItem>>>(UiState.Loading)
    val doctorState: StateFlow<UiState<List<DoctorItem>>> = _doctorState.asStateFlow()


    init {
        getCurrentUserInfo()
    }
    private fun getCurrentUserInfo() {
        val currentUser = firebaseAuth.currentUser
        _userName.value = currentUser?.displayName
        _profileImageUrl.value = currentUser?.photoUrl?.toString()
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