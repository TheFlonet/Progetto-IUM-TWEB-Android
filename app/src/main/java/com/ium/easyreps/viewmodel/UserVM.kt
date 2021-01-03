package com.ium.easyreps.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ium.easyreps.model.User

class UserVM : ViewModel() {
    var currentUser = MutableLiveData(User())
}