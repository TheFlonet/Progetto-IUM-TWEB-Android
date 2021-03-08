package com.ium.easyreps.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ium.easyreps.R
import com.ium.easyreps.adapter.RecyclerLessonsAdapter
import com.ium.easyreps.model.Course
import com.ium.easyreps.model.PrivateLesson
import com.ium.easyreps.viewmodel.CoursesVM
import com.ium.easyreps.viewmodel.UserVM

class CoursesList(var pos: Int) : Fragment() {
    private lateinit var coursesAdapter: RecyclerLessonsAdapter
    private lateinit var coursesRecycler: RecyclerView
    private lateinit var mView: View
    private var isLogged = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_courses_list, container, false) as View
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        UserVM.user.observe(viewLifecycleOwner, {
            isLogged = it.isLogged
        })

        coursesRecycler = view.findViewById(R.id.coursesRecycler)
        val linearLayoutManager = LinearLayoutManager(activity)
        coursesRecycler.layoutManager = linearLayoutManager
        coursesAdapter = RecyclerLessonsAdapter(
            CoursesVM.courses.value!![pos],
            UserVM.user.value!!.isLogged,
            UserVM.user.value!!.name
        )
        coursesRecycler.adapter = coursesAdapter
        coursesRecycler.setHasFixedSize(true)

        CoursesVM.courses.observe(viewLifecycleOwner, {
            coursesAdapter.notifyDataSetChanged()
        })

    }
}