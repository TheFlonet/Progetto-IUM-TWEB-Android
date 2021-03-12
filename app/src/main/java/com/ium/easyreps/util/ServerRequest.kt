package com.ium.easyreps.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ium.easyreps.R
import com.ium.easyreps.model.Course
import com.ium.easyreps.model.PrivateLesson
import com.ium.easyreps.model.Teacher
import com.ium.easyreps.model.User
import com.ium.easyreps.viewmodel.CoursesVM
import com.ium.easyreps.viewmodel.UserVM
import org.json.JSONArray

object ServerRequest {
    private var queue: RequestQueue? = null

    fun login(context: Context, username: String, password: String, callback: () -> Unit) {
        init(context)
        queue!!.add(
            JsonObjectRequest(Request.Method.GET,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.login}&nome=$username&password=$password",
                null,
                {
                    UserVM.user.value?.isLogged = it.getBoolean("loggedIn")
                    if (UserVM.user.value?.isLogged == true) {
                        UserVM.user.value?.name = it.getString("username")
                        UserVM.user.value?.isAdmin = it.getBoolean("isAdmin")
                        UserVM.user.value?.password = password
                    }
                    Config.session = it.getString("SessionID")
                    callback()
                },
                {})
        )
    }

    private fun init(context: Context) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context)
        }
    }

    fun logout(context: Context, callback: () -> Unit) {
        init(context)
        queue!!.add(
            JsonObjectRequest(
                Request.Method.GET,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.logout}",
                null,
                {
                    UserVM.user.value = User()
                    Config.session = null
                    callback()
                },
                {
                    UserVM.user.value = User()
                    Config.session = null
                    callback()
                })
        )
    }

    fun getCourses(context: Context) {
        init(context)
        queue!!.add(JsonArrayRequest(
            Request.Method.GET,
            "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.getPrivateLessons}",
            null, {
                if (getBusyCourses(context))
                    parseCoursesJson(it)
                else
                    Toast.makeText(
                        context, context.getString(R.string.error_getting_courses),
                        Toast.LENGTH_LONG
                    ).show()
            }, {
                Toast.makeText(
                    context, context.getString(R.string.error_getting_courses),
                    Toast.LENGTH_LONG
                ).show()
            })
        )
    }

    private fun parseCoursesJson(jsonArray: JSONArray?) {
        if (jsonArray != null) {
            for (i in (0 until jsonArray.length())) {
                val input = jsonArray.getJSONObject(i)
                val subject = input.getJSONObject("corso")
                val teacher = input.getJSONObject("docente")
                for (j in (0 until 5)) {
                    for (h in (15 until 19))
                        CoursesVM.courses[j].value!!.add(
                            PrivateLesson(
                                Course(
                                    subject.getInt("ID"),
                                    subject.getString("titolo")
                                ),
                                Teacher(
                                    teacher.getInt("ID"),
                                    teacher.getString("nome"),
                                    teacher.getString("cognome")
                                ), Day.getDay(j), h
                            )
                        )
                    CoursesVM.courses[j].value!!.sort()
                    Log.d("COURSES$j", CoursesVM.courses[j].value!!.size.toString())
                }
            }
        }
    }

    private fun getBusyCourses(context: Context): Boolean {
        init(context)
        queue!!.add(
            JsonArrayRequest(
                Request.Method.GET,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.getActiveReservations}",
                null,
                {
                    for (i in (0 until it.length())) {
                        var dayCourses = it.getJSONObject(i)
                        // TODO creare esempio da sottrarre

                    }
                    Log.d("SERVER_SUCCESS", it.toString())
                },
                {
                    Toast.makeText(
                        context, context.getString(R.string.error_getting_courses),
                        Toast.LENGTH_LONG
                    ).show()
                    Log.d("SERVER_FAIL", it.toString())
                })
        )

        return true
    }

    fun getHistory(context: Context) {
        init(context)
        val url =
            "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.getReservations}&utente=${UserVM.user.value?.name}&Session=${Config.session}"
        queue!!.add(
            JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                { Log.d("SERVER_SUCCESS", it.toString()) },
                { Log.d("SERVER_FAIL", it.toString()) })
        )
        Log.d("SERVER", Config.session!!)
    }
}