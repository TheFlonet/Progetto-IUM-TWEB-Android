package com.ium.easyreps.adapter

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.ium.easyreps.R
import com.ium.easyreps.util.Config
import com.ium.easyreps.model.Course
import com.ium.easyreps.model.PrivateLesson
import com.ium.easyreps.model.Teacher
import com.ium.easyreps.util.Day
import com.ium.easyreps.view.CoursesList
import org.json.JSONObject

class TabLessonsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private lateinit var fragments: ArrayList<Fragment>

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun initFragments(context: Context) {
        fragments = ArrayList(5)

        // TODO controllare come ottenere i corsi e fare il parsing
        val coursesArray = JsonArrayRequest(
            Request.Method.GET,
            "${Config.ip}:${Config.port}/${Config.servlet}?action=",
            null, {
                // days
                for (i in (0 until it.length())) {
                    val coursesOfTheDay = it.getJSONArray(i)
                    val coursesRes = ArrayList<PrivateLesson>(coursesOfTheDay.length())

                    for (j in (0 until coursesOfTheDay.length())) {
                        coursesRes[j] = parseCourse(coursesOfTheDay.getJSONObject(j), Day.getDay(i))
                    }
                    fragments[i] = CoursesList(coursesRes)
                }
            }, {
                Toast.makeText(
                    context, context.getString(R.string.error_getting_courses),
                    Toast.LENGTH_LONG
                ).show()
            })

        Volley.newRequestQueue(context).add(coursesArray)
    }

    private fun parseCourse(courseJson: JSONObject, day: Day): PrivateLesson {
        val course = courseJson.getJSONObject("Course")
        val teacher = courseJson.getJSONObject("Teacher")

        return PrivateLesson(
            Course(course.getInt("id"), course.getString("name")),
            Teacher(
                teacher.getInt("id"),
                teacher.getString("name"),
                teacher.getString("surname")
            ), day, courseJson.getInt("startAt")
        )
    }
}