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
import com.ium.easyreps.adapter.RecyclerLessonsAdapter
import com.ium.easyreps.model.PrivateLesson
import com.ium.easyreps.viewmodel.CoursesVM
import com.ium.easyreps.viewmodel.UserVM

class CoursesList(var pos: Int) : Fragment() {
    private lateinit var coursesAdapter: RecyclerLessonsAdapter
    private lateinit var coursesRecycler: RecyclerView
    private lateinit var mView: View
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_courses_list, container, false) as View
        swipeRefreshLayout = mView.findViewById(R.id.swipeCourses)
        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            coursesAdapter.updateData(CoursesVM.courses[pos].value!!)
        }
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coursesRecycler = view.findViewById(R.id.coursesRecycler)
        val linearLayoutManager = LinearLayoutManager(activity)
        coursesRecycler.layoutManager = linearLayoutManager

        coursesAdapter = RecyclerLessonsAdapter(
            CoursesVM.courses[pos].value!!.clone() as ArrayList<PrivateLesson>
        )

        coursesRecycler.adapter = coursesAdapter
        coursesRecycler.setHasFixedSize(true)

        CoursesVM.courses[pos].observe(viewLifecycleOwner, {
            coursesAdapter.updateData(it)
        })
    }
}