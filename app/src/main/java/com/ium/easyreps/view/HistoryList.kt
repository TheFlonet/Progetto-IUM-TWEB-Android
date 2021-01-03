package com.ium.easyreps.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ium.easyreps.R
import com.ium.easyreps.adapter.RecyclerReservationAdapter
import com.ium.easyreps.model.Reservation

class HistoryList(var reservationList: ArrayList<Reservation>) : Fragment(), SwipeRefreshLayout.OnRefreshListener {
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
        reservationAdapter = RecyclerReservationAdapter(reservationList) // TODO creare la lista di corsi come richiesta dal web
        reservationRecycler.adapter = reservationAdapter
        reservationRecycler.setHasFixedSize(true)
        setupSwipeRefresh()
    }

    override fun onRefresh() {
        updateData()
        mView.findViewById<SwipeRefreshLayout>(R.id.historySwipe).isRefreshing = false
    }

    private fun updateData() {
        reservationList.shuffle() // TODO aggiornare veramente la lista
        reservationRecycler.removeAllViews()
        reservationAdapter = RecyclerReservationAdapter(reservationList)
        reservationRecycler.adapter = reservationAdapter
    }

    private fun setupSwipeRefresh() {
        val swipeContainer = mView.findViewById<SwipeRefreshLayout>(R.id.historySwipe)
        swipeContainer.setOnRefreshListener(this)
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_light,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }
}