package com.ium.easyreps.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ium.easyreps.model.PrivateLesson

object CoursesVM {
    var courses = arrayListOf(
        ArrayList<PrivateLesson>(),
        ArrayList(),
        ArrayList(),
        ArrayList(),
        ArrayList()
    )

    var busyCourses = MutableLiveData(
        arrayListOf(
            ArrayList<PrivateLesson>(),
            ArrayList(),
            ArrayList(),
            ArrayList(),
            ArrayList()
        )
    )
}