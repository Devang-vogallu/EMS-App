package com.ttt.employeeapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ttt.employeeapplication.Repository.UserRepository
import com.ttt.employeeapplication.ViewModel.EmployeeViewModel

class EmployeeViewModelFactory(private val employeeRepository: UserRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeeViewModel(employeeRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}