package com.example.appointmentbookingapp.presentation.ui.home.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.appointmentbookingapp.domain.model.DoctorItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SharedDoctorViewModel @Inject constructor(): ViewModel() {

    private var _selectedDoctor = MutableStateFlow<DoctorItem>(DoctorItem())
    val selectedDoctor = _selectedDoctor.asStateFlow()


    suspend fun setSelectedDoctor(doctorItem: DoctorItem){
        _selectedDoctor.emit(doctorItem)
        Log.d("SharedDoctorViewModel", "setSelectedDoctor: $doctorItem")
    }

}
