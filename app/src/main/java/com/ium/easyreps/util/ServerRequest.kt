package com.ium.easyreps.util

import android.app.Application
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
import com.ium.easyreps.view.CoursesList
import com.ium.easyreps.view.HistoryList
import com.ium.easyreps.viewmodel.UserVM
import org.json.JSONObject


object ServerRequest {
    var queue: RequestQueue? = null
    private const val setCookieKey = "Set-Cookie"
    private const val cookieKey = "Cookie"
    private const val sessionCookie = "sessionid"
    private var preferences: SharedPreferences? = null

    fun login(context: Context, username: String, password: String, callback: () -> Unit) {
        init(context)
        queue!!.add(
            CustomRequest(
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.login}&nome=$username&password=$password",
                JsonObject::class.java,
                {
                    UserVM.user.value?.isLogged = it["loggedIn"].asBoolean
                    if (UserVM.user.value?.isLogged == true) {
                        UserVM.user.value?.name = it["username"].asString
                        UserVM.user.value?.isAdmin = it["isAdmin"].asBoolean
                        UserVM.user.value?.password = password
                    }

                    callback()
                },
                {}
            )
        )
    }

    private fun init(context: Context) {
        if (queue == null)
            queue = Volley.newRequestQueue(context)
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

    fun getCourses(
        context: Context,
        fragments: List<CoursesList>
    ) {
        init(context)
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
        init(context)
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
        init(context)
        queue!!.add(
            CustomRequest(
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.getReservations}&utente=${UserVM.user.value?.name}",
                JsonObject::class.java,
                {
                    Log.d("SUCCESSO_HISTORY", it.toString())
                },
                {
                    Log.d("FALLITO_HISTORY", it.toString())
                    //header["Cookie"]?.let { it1 -> Log.d("PROVA", it1) }
                }
            )
        )

        /*checkSession(context) {
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
        }*/
    }

    fun checkSessionCookie(headers: Map<String?, String>) {
        if (headers.containsKey(setCookieKey)
            && headers[setCookieKey]!!.startsWith(sessionCookie)
        ) {
            var cookie = headers[setCookieKey]
            if (cookie!!.isNotEmpty()) {
                val splitCookie = cookie.split(";".toRegex()).toTypedArray()
                val splitSessionId = splitCookie[0].split("=".toRegex()).toTypedArray()
                cookie = splitSessionId[1]
                val prefEditor: SharedPreferences.Editor = preferences!!.edit()
                prefEditor.putString(sessionCookie, cookie)
                prefEditor.apply()

                Log.d("PREFERENZE", preferences.toString())
            }
            Log.d("PREFERENZE_FUORI_1", preferences.toString())
        }
        Log.d("PREFERENZE_FUORI_2", preferences.toString())
    }

    fun addSessionCookie(headers: MutableMap<String?, String?>) {
        val sessionId: String = preferences!!.getString(sessionCookie, "")!!
        if (sessionId.isNotEmpty()) {
            val builder = StringBuilder()
            builder.append(sessionCookie)
            builder.append("=")
            builder.append(sessionId)
            if (headers.containsKey(cookieKey)) {
                builder.append("; ")
                builder.append(headers[cookieKey])
            }
            headers[cookieKey] = builder.toString()
        }
    }
}