package com.ium.easyreps.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.preference.PreferenceManager
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonObject
import com.ium.easyreps.R
import com.ium.easyreps.model.PrivateLesson
import com.ium.easyreps.model.User
import com.ium.easyreps.view.HistoryList
import com.ium.easyreps.viewmodel.CoursesVM
import com.ium.easyreps.viewmodel.Session
import com.ium.easyreps.viewmodel.UserVM
import com.squareup.okhttp.*
import org.json.JSONArray
import org.json.JSONObject

object ServerRequest {
    var queue: RequestQueue? = null
    var client: OkHttpClient? = null
    private var preferences: SharedPreferences? = null

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
                    Session.session.value = it.getString("SessionID")

                    callback()
                },
                {})
        )
    }

    private fun init(context: Context) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context)
        }
        if (client == null) {
            client = OkHttpClient()
        }
        if (preferences == null) preferences =
            PreferenceManager.getDefaultSharedPreferences(context)
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
                    callback()
                },
                {
                    UserVM.user.value = User()
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
                createFragments(it)
                removeBookedCourses(context)
            }, {
                Toast.makeText(
                    context, context.getString(R.string.error_getting_courses),
                    Toast.LENGTH_LONG
                ).show()
                Log.d("SERVER", it.toString())
            })
        )
    }

    private fun removeBookedCourses(context: Context) {
        init(context)
        queue!!.add(
            JsonArrayRequest(
                Request.Method.GET,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.getActiveReservations}",
                null,
                {
                    /*for ((i, fragment) in fragments.withIndex()) {
                        val coursesOfTheDay = it.getJSONObject(i)
                        for (course in CoursesVM.courses.value!!) {
                            // TODO creare esempio da sottrarre
                        }
                    }*/

                },
                {
                    Toast.makeText(
                        context, context.getString(R.string.error_getting_courses),
                        Toast.LENGTH_LONG
                    ).show()
                })
        )
    }

    private fun createFragments(webResponse: JSONArray?) {
        /*for (i in (0 until 5)) {
            val courses = ArrayList<PrivateLesson>()
            for (j in (15 until 19))
                if (webResponse?.keys() != null)
                    for (z in webResponse.keys())
                        for (t in (0 until webResponse.getJSONArray(z).length()))
                            courses.add(
                                PrivateLesson(
                                    z.toString(),
                                    webResponse.getJSONArray(z).getString(t),
                                    Day.getDay(i), j
                                )
                            )
            CoursesVM.courses.value!![i].addAll(courses)
        }*/
    }

    fun getHistory(context: Context) {
        init(context)
        val url =
            "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.getReservations}&utente=${UserVM.user.value?.name}&Session=${Session.session.value}"
        queue!!.add(
            JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                { Log.d("SERVER_SUCCESS", it.toString()) },
                { Log.d("SERVER_FAIL", it.toString()) })
        )
    }
}