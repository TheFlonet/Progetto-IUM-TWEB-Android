package com.ium.easyreps.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ium.easyreps.R
import com.ium.easyreps.adapter.RecyclerReservationAdapter
import com.ium.easyreps.model.Reservation

class HistoryList(var reservationList: ArrayList<Reservation>) : Fragment() {
    private lateinit var reservationAdapter: RecyclerReservationAdapter
    private lateinit var reservationRecycler: RecyclerView
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_history_list, container, false) as View
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reservationRecycler = view.findViewById(R.id.historyRecycler)
        val linearLayoutManager = LinearLayoutManager(activity)
        reservationRecycler.layoutManager = linearLayoutManager
        reservationAdapter = RecyclerReservationAdapter(reservationList)
        reservationRecycler.adapter = reservationAdapter
        reservationRecycler.setHasFixedSize(true)
    }
}