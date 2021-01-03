package com.ium.easyreps.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class UserVMFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserVM::class.java)) {
            return UserVM() as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}