package com.ium.easyreps.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ium.easyreps.model.PrivateLesson

object CoursesVM {
    var courses = MutableLiveData(
        arrayListOf(
            ArrayList<PrivateLesson>(),
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList()
        )
    )
}