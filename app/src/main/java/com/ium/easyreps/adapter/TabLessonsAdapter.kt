package com.ium.easyreps.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class TabLessonsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private lateinit var fragments: ArrayList<Fragment>

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun initFragments() {
        // TODO richiedere le 5 liste dal server (1 per ogni giorno)
        fragments = arrayListOf(
            /*CoursesList(mon),
            CoursesList(tue),
            CoursesList(wed),
            CoursesList(thu),
            CoursesList(fri)*/
        )
    }
}