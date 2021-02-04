package com.ium.easyreps.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ium.easyreps.model.User

/*class UserVM : ViewModel() {
    var currentUser = MutableLiveData(User())
}*/

object UserVM {
    var user = MutableLiveData(User())
}

/*class UserVM private constructor() {
    var currentUser = MutableLiveData(User())

    private object HOLDER {
        val INSTANCE = UserVM()
    }

    companion object {
        val instance: UserVM by lazy { HOLDER.INSTANCE }
    }
}*/