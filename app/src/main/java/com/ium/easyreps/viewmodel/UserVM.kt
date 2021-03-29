package com.ium.easyreps.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ium.easyreps.model.User

object UserVM {
    var user = MutableLiveData(User())
}