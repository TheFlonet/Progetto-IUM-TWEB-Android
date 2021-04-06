package com.ium.easyreps.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ium.easyreps.R
import com.ium.easyreps.adapter.RecyclerReservationAdapter
import com.ium.easyreps.model.Reservation
import com.ium.easyreps.viewmodel.ReservationVM

class HistoryList(private var pos: Int) : Fragment() {
    private lateinit var reservationAdapter: RecyclerReservationAdapter
    private lateinit var reservationRecycler: RecyclerView
    private lateinit var mView: View
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_history_list, container, false) as View
        swipeRefreshLayout = mView.findViewById(R.id.swipeHistory)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            reservationAdapter.updateData(ReservationVM.reservations[pos].value!!)
        }
        return mView
    }

    @Suppress("UNCHECKED_CAST")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        reservationRecycler = view.findViewById(R.id.historyRecycler)
        val linearLayoutManager = LinearLayoutManager(activity)
        reservationRecycler.layoutManager = linearLayoutManager

        reservationAdapter = RecyclerReservationAdapter(ReservationVM.reservations[pos].value!!.clone() as ArrayList<Reservation>)

        reservationRecycler.adapter = reservationAdapter
        reservationRecycler.setHasFixedSize(true)

        ReservationVM.reservations[pos].observe(viewLifecycleOwner, {
            reservationAdapter.updateData(it)
        })
    }
}