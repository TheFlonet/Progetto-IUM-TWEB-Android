package com.ium.easyreps.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ium.easyreps.model.PrivateLesson
import com.ium.easyreps.util.ServerRequest
import com.ium.easyreps.view.CoursesList

class TabLessonsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private var fragments: List<CoursesList> = arrayListOf(
        CoursesList(ArrayList()),
        CoursesList(ArrayList()),
        CoursesList(ArrayList()),
        CoursesList(ArrayList()),
        CoursesList(ArrayList())
    )

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun initFragments(context: Context) {
        for (fragment in fragments) {
            fragment.coursesList = ArrayList()
        }
        ServerRequest.getCourses(context, fragments)
    }
}
