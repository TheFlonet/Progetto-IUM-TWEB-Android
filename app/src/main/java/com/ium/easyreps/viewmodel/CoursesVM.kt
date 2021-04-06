package com.ium.easyreps.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ium.easyreps.model.PrivateLesson

object CoursesVM {
    var courses = arrayListOf(
        MutableLiveData(ArrayList<PrivateLesson>()),
        MutableLiveData(ArrayList()),
        MutableLiveData(ArrayList()),
        MutableLiveData(ArrayList()),
        MutableLiveData(ArrayList())
    )
}