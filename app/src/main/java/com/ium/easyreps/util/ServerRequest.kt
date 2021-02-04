package com.ium.easyreps.util

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.*
import com.ium.easyreps.R
import com.ium.easyreps.model.PrivateLesson
import com.ium.easyreps.model.User
import com.ium.easyreps.view.CoursesList
import com.ium.easyreps.view.HistoryList
import com.ium.easyreps.viewmodel.UserVM
import org.json.JSONObject
import java.io.UnsupportedEncodingException
import java.net.CookieManager


object ServerRequest {
    var queue: RequestQueue? = null

    fun login(context: Context, username: String, password: String, callback: () -> Unit) {
        initQueue(context)
        queue!!.add(JsonObjectRequest(
            Request.Method.GET,
            "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.login}&nome=$username&password=$password",
            null,
            {
                UserVM.user.value?.isLogged = it.getBoolean("loggedIn")
                if (UserVM.user.value?.isLogged == true) {
                    UserVM.user.value?.name = it.getString("username")
                    UserVM.user.value?.isAdmin = it.getBoolean("isAdmin")
                    UserVM.user.value?.password = password
                }
                callback()
            }, {})
        )
    }

    private fun initQueue(context: Context) {
        var request = JsonArrayRequest(Request.Method.GET, "", {}, {}, {})
        val getRequest: StringRequest = object : StringRequest(
            Method.GET, "",
            Response.Listener { val x: String = CookieManager().getCookieValue() },
            Response.ErrorListener { error ->
                Log.d("ERROR", "Error => $error")
            }
        ) {
            override fun parseNetworkResponse(response: NetworkResponse): Response<String> {
                return try {
                    val jsonString =
                        String(response.data, HttpHeaderParser.parseCharset(response.headers))
                    val headerResponse = response.headers.values.toString()
                    val index1 = headerResponse.indexOf("PHPSESSID=")
                    val index2 = headerResponse.indexOf("; path")
                    var sessionId = headerResponse.substring(index1, index2)
                    Response.success(jsonString, HttpHeaderParser.parseCacheHeaders(response))
                } catch (e: UnsupportedEncodingException) {
                    Response.error(ParseError(e))
                }
            }
        }
        if (queue == null) {
            queue = Volley.newRequestQueue(context)
        }
    }

    fun logout(context: Context, callback: () -> Unit) {
        initQueue(context)
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

    fun getCourses(
        context: Context,
        fragments: List<CoursesList>
    ) {
        initQueue(context)
        queue!!.add(JsonObjectRequest(
            Request.Method.GET,
            "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.getPrivateLessons}",
            null, {
                createFragments(it, fragments)
                removeBookedCourses(context, fragments)
            }, {
                Toast.makeText(
                    context, context.getString(R.string.error_getting_courses),
                    Toast.LENGTH_LONG
                ).show()
            })
        )
    }

    private fun removeBookedCourses(context: Context, fragments: List<CoursesList>) {
        initQueue(context)
        queue!!.add(
            JsonArrayRequest(
                Request.Method.GET,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.getActiveReservations}",
                null,
                {
                    for ((i, fragment) in fragments.withIndex()) {
                        val coursesOfTheDay = it.getJSONObject(i)
                        for (course in fragment.coursesList!!) {
                            // TODO creare esempio da sottrarre
                        }
                    }

                },
                {
                    Toast.makeText(
                        context, context.getString(R.string.error_getting_courses),
                        Toast.LENGTH_LONG
                    ).show()
                })
        )
    }

    private fun createFragments(webResponse: JSONObject?, fragments: List<CoursesList>) {
        for (i in (0 until 5)) {
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
            fragments[i].coursesList = courses
        }
    }

    fun getHistory(context: Context, fragments: ArrayList<HistoryList>) {
        checkSession(context) {
            // TODO controllare parsing prenotazioni e capire come mai mi considera non loggato
            initQueue(context)
            queue!!.add(
                JsonObjectRequest(Request.Method.GET,
                    "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.getReservations}&utente=${UserVM.user.value?.name}",
                    null,
                    {
                        Log.d("mHistory_SUCCESS", it.toString())

                        if (it.getBoolean("error")) {
                            Toast.makeText(
                                context, context.getString(R.string.error_getting_courses),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    },
                    {
                        Toast.makeText(
                            context, context.getString(R.string.error_getting_courses),
                            Toast.LENGTH_LONG
                        ).show()
                    })
            )
        }
    }

    private fun checkSession(context: Context, callback: () -> Unit) {
        initQueue(context)
        queue!!.add(
            JsonObjectRequest(
                Request.Method.GET,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.checkSession}&username=${UserVM.user.value?.name}&isAdmin=${UserVM.user.value?.isAdmin}",
                null,
                {
                    if (!it.getBoolean("isLogged"))
                        UserVM.user.value?.name?.let { name ->
                            UserVM.user.value?.password?.let { password ->
                                login(
                                    context,
                                    name, password, callback
                                )
                            }
                        }
                },
                {
                    Toast.makeText(
                        context, context.getString(R.string.error_getting_courses),
                        Toast.LENGTH_LONG
                    ).show()
                })
        )
    }
}