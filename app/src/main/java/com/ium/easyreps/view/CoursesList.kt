package com.ium.easyreps.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ium.easyreps.R
import com.ium.easyreps.adapter.RecyclerLessonsAdapter
import com.ium.easyreps.model.PrivateLesson
import com.ium.easyreps.viewmodel.UserVM

class CoursesList(var coursesList: ArrayList<PrivateLesson>) : Fragment() {
    private lateinit var coursesAdapter: RecyclerLessonsAdapter
    private lateinit var coursesRecycler: RecyclerView
    private lateinit var mView: View
    private val model: UserVM by activityViewModels()
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

        model.currentUser.observe(viewLifecycleOwner, {
            isLogged = it.isLogged
        })

        coursesRecycler = view.findViewById(R.id.coursesRecycler)
        val linearLayoutManager = LinearLayoutManager(activity)
        coursesRecycler.layoutManager = linearLayoutManager
        coursesAdapter = RecyclerLessonsAdapter(coursesList, isLogged)
        coursesRecycler.adapter = coursesAdapter
        coursesRecycler.setHasFixedSize(true)
    }
}