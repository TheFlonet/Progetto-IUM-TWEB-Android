package com.ium.easyreps.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ium.easyreps.model.Course
import com.ium.easyreps.model.Reservation
import com.ium.easyreps.model.Teacher
import com.ium.easyreps.model.User
import com.ium.easyreps.view.HistoryList
import com.ium.easyreps.util.Day
import com.ium.easyreps.util.State

class TabHistoryAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private lateinit var fragments: ArrayList<Fragment>

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun initFragments() {
        // TODO richiedere le 3 liste dal server (1 per ogni stato)
        fragments = arrayListOf(/*HistoryList(active), HistoryList(done), HistoryList(cancelled)*/)
    }
}
