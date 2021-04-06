package com.ium.easyreps.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ium.easyreps.util.ServerRequest
import com.ium.easyreps.view.HistoryList

class TabHistoryAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var fragments: List<HistoryList> = arrayListOf(
        HistoryList(0),
        HistoryList(1),
        HistoryList(2)
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun initFragments(context: Context) {
        ServerRequest.getHistory(context)
    }
}
