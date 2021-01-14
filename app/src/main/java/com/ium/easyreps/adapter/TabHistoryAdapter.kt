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
import com.ium.easyreps.model.Reservation
import com.ium.easyreps.model.Teacher
import com.ium.easyreps.util.Day
import com.ium.easyreps.util.State
import com.ium.easyreps.view.HistoryList
import org.json.JSONObject

class TabHistoryAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    private lateinit var fragments: ArrayList<Fragment>

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }

    fun initFragments(context: Context) {
        fragments = ArrayList(3)

        // TODO controllare parsing prenotazioni
        val coursesArray = JsonArrayRequest(
            Request.Method.GET,
            "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.getReservations}",
            null, {
                for (i in (0 until it.length())) {
                    val bookedLessons = it.getJSONArray(i)
                    val res = ArrayList<Reservation>(bookedLessons.length())

                    for (j in (0 until bookedLessons.length())) {
                        res[j] =
                            parseReservations(bookedLessons.getJSONObject(j), State.getState(i))
                    }
                    fragments[i] = HistoryList(res)
                }
            }, {
                Toast.makeText(
                    context, context.getString(R.string.error_getting_courses),
                    Toast.LENGTH_LONG
                ).show()
            }
        )

        Volley.newRequestQueue(context).add(coursesArray)
    }

    private fun parseReservations(reservationJson: JSONObject, state: State): Reservation {
        val course = reservationJson.getJSONObject("Course")
        val teacher = reservationJson.getJSONObject("Teacher")

        return Reservation(
            reservationJson.getInt("id"),
            Course(course.getInt("id"), course.getString("name")),
            Teacher(
                teacher.getInt("id"),
                teacher.getString("name"),
                teacher.getString("surname")
            ), state,
            Day.getDay(reservationJson.getInt("day")),
            reservationJson.getInt("startAt")
        )
    }
}
