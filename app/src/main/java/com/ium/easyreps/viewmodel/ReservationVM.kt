package com.ium.easyreps.viewmodel

import androidx.lifecycle.MutableLiveData
import com.ium.easyreps.model.Reservation

object ReservationVM {
    var reservations = arrayListOf(
        MutableLiveData(ArrayList<Reservation>()), // active
        MutableLiveData(ArrayList()), // done
        MutableLiveData(ArrayList()) // cancelled
    )
}