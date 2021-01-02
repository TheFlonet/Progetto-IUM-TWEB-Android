package com.ium.easyreps.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.ium.easyreps.R
import com.ium.easyreps.adapter.RecyclerLessonsAdapter
import com.ium.easyreps.dto.PrivateLesson

class CoursesList(var coursesList: ArrayList<PrivateLesson>) : Fragment(), SwipeRefreshLayout.OnRefreshListener {
    private lateinit var coursesAdapter: RecyclerLessonsAdapter
    private lateinit var coursesRecycler: RecyclerView
    private lateinit var mView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mView = inflater.inflate(R.layout.fragment_courses_list, container, false) as View
        return mView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        coursesRecycler = view.findViewById(R.id.coursesRecycler)
        val linearLayoutManager = LinearLayoutManager(activity)
        coursesRecycler.layoutManager = linearLayoutManager
        coursesAdapter = RecyclerLessonsAdapter(coursesList) // TODO creare la lista di corsi come richiesta dal web
        coursesRecycler.adapter = coursesAdapter
        coursesRecycler.setHasFixedSize(true)
        setupSwipeRefresh()
    }

    override fun onRefresh() {
        updateData()
        mView.findViewById<SwipeRefreshLayout>(R.id.coursesSwipe).isRefreshing = false
    }

    private fun updateData() {
        coursesList.shuffle() // TODO aggiornare veramente la lista
        coursesRecycler.removeAllViews()
        coursesAdapter = RecyclerLessonsAdapter(coursesList)
        coursesRecycler.adapter = coursesAdapter
    }

    private fun setupSwipeRefresh() {
        val swipeContainer = mView.findViewById<SwipeRefreshLayout>(R.id.coursesSwipe)
        swipeContainer.setOnRefreshListener(this)
        swipeContainer.setColorSchemeResources(
            android.R.color.holo_blue_light,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        )
    }
}