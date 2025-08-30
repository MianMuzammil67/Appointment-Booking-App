package com.example.appointmentbookingapp.presentation.ui.sharedviewmodel

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@HiltViewModel
class SharedCategoryViewModel @Inject constructor(): ViewModel() {
    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    fun setSelectedCategory(category: String?) {
        _selectedCategory.value = category
    }
}