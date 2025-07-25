package com.example.hackaton2025.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hackaton2025.data.repository.EducationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OverviewViewModel @Inject constructor(
    private val repository: EducationRepository
): ViewModel() {
    init {
        viewModelScope.launch {
            repository.getUsers().collect {
                Log.e("overview", it.toString())
            }
        }

        viewModelScope.launch {
            repository.getChildTasks(7).collect {
                Log.e("overview task", it.toString())
            }
        }
    }
}