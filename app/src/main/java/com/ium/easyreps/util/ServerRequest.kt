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
import com.ium.easyreps.model.*
import com.ium.easyreps.viewmodel.*
import org.json.JSONArray
import org.json.JSONException

object ServerRequest {
    private var queue: RequestQueue? = null

    private fun init(context: Context) {
        if (queue == null) {
            queue = Volley.newRequestQueue(context)
        }
    }

    fun login(context: Context, username: String, password: String, callback: () -> Unit) {
        init(context)
        queue!!.add(JsonObjectRequest(Request.Method.GET,
            "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.login}&nome=$username&password=$password",
            null, {
                if (it.getBoolean("loggedIn")) {
                    UserVM.user.value?.isLogged = it.getBoolean("loggedIn")
                    UserVM.user.value?.name = it.getString("username")
                    UserVM.user.value?.password = password
                    Config.session = it.getString("SessionID")
                }
                callback()
            }, {})
        )
    }

    fun logout(context: Context, callback: () -> Unit) {
        init(context)
        queue!!.add(
            JsonObjectRequest(Request.Method.GET,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.logout}&Session=${Config.session}",
                null, { invalidateLogin(callback) }, { invalidateLogin(callback) })
        )
    }

    private fun invalidateLogin(callback: () -> Unit) {
        UserVM.user.value = User()
        Config.session = null
        callback()
    }

    fun getCourses(context: Context) {
        init(context)
        queue!!.add(JsonArrayRequest(
            Request.Method.GET,
            "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.getPrivateLessons}",
            null, {
                parseCoursesJson(it, context)
            }, {
                Toast.makeText(
                    context,
                    context.getString(R.string.error_getting_courses),
                    Toast.LENGTH_LONG
                ).show()
            })
        )
    }

    private fun parseCoursesJson(jsonArray: JSONArray?, context: Context) {
        if (jsonArray != null) {
            for (i in CoursesVM.courses)
                i.value!!.clear()
            for (i in (0 until jsonArray.length())) {
                val input = jsonArray.getJSONObject(i)
                val subject = input.getJSONObject("corso")
                val teacher = input.getJSONObject("docente")
                for (j in (0 until 5)) {
                    for (h in (15 until 19)) {
                        val lessonToAdd = PrivateLesson(
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
                        val target = CoursesVM.courses[j].value!!
                        if (!target.contains(lessonToAdd))
                            target.add(lessonToAdd)
                    }
                    CoursesVM.courses[j].value!!.sort()
                }
            }
        }
        removeBusyCourses(context)
    }

    private fun removeBusyCourses(context: Context) {
        queue!!.add(
            JsonArrayRequest(
                Request.Method.GET,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.getActiveReservations}",
                null,
                {
                    for (i in (0 until it.length())) {
                        val day = it.getJSONObject(i)
                        for (j in (15 until 19)) {
                            var hour: JSONArray?
                            try {
                                hour = day.getJSONArray(j.toString())
                                for (z in (0 until hour!!.length())) {
                                    val reservation = hour.getJSONObject(z)
                                    val dayLessons = CoursesVM.courses[i].value!!.iterator()
                                    while (dayLessons.hasNext()) {
                                        val lesson = dayLessons.next()
                                        if (lesson.course.name == reservation.getString("corso")
                                            && lesson.teacher.surname == reservation.getString("docente")
                                            && lesson.startAt == j
                                        ) {
                                            dayLessons.remove()
                                        }
                                    }
                                }
                            } catch (e: JSONException) {
                                continue
                            }
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

    fun cancelRequest(context: Context, reservation: Reservation) {
        if (NetworkUtil.checkConnection(context)) {
            val cancelRequest = JsonObjectRequest(
                Request.Method.POST,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.cancel}&id=${reservation.id}",
                null,
                {
                    ReservationVM.reservations[0].value!!.remove(reservation)
                    ReservationVM.reservations[2].value!!.add(reservation)
                    Toast.makeText(
                        context,
                        context.getString(R.string.discard_confirmation),
                        Toast.LENGTH_LONG
                    ).show()
                },
                {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_cancel),
                        Toast.LENGTH_LONG
                    ).show()
                })

            Volley.newRequestQueue(context).add(cancelRequest)
        } else {
            Toast.makeText(context, context.getString(R.string.no_connectivity), Toast.LENGTH_LONG)
                .show()
        }
    }

    fun doneRequest(context: Context, reservation: Reservation) {
        init(context)
        queue!!.add(
            JsonObjectRequest(
                Request.Method.GET,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.setReservationDone}&id=${reservation.id}&Session=${Config.session}",
                null,
                {
                    ReservationVM.reservations[0].value!!.remove(reservation)
                    ReservationVM.reservations[1].value!!.add(reservation)
                    Toast.makeText(
                        context,
                        context.getString(R.string.done_confirmation),
                        Toast.LENGTH_LONG
                    ).show()
                },
                {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_done),
                        Toast.LENGTH_LONG
                    ).show()
                })
        )
    }

    fun bookRequest(context: Context, lesson: PrivateLesson) {
        val url =
            "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.book}&Session=${Config.session}&idCorso=${lesson.course.id}&idDocente=${lesson.teacher.id}&ora=${lesson.startAt}&day=${
                Day.toIta(
                    lesson.day
                )
            }"
        if (NetworkUtil.checkConnection(context)) {
            val cancelRequest = JsonObjectRequest(
                Request.Method.POST,
                url,
                null,
                {
                    if (it.getBoolean("error")) {
                        Toast.makeText(
                            context,
                            context.getString(R.string.error_booking),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            context.getString(R.string.book_confirmation),
                            Toast.LENGTH_LONG
                        ).show()
                        CoursesVM.courses[Day.getDayNum(lesson.day)].value!!.remove(lesson)
                    }
                },
                {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_booking),
                        Toast.LENGTH_LONG
                    ).show()
                })

            Volley.newRequestQueue(context).add(cancelRequest)
        } else {
            Toast.makeText(context, context.getString(R.string.no_connectivity), Toast.LENGTH_LONG)
                .show()
        }
    }

    fun getHistory(context: Context) {
        init(context)
        queue!!.add(
            JsonArrayRequest(
                Request.Method.GET,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.getReservations}&utente=${UserVM.user.value!!.name}&Session=${Config.session}",
                null,
                {
                    for (i in (0 until 3))
                        ReservationVM.reservations[i].value!!.clear()
                    for (i in (0 until it.length())) {
                        val res = it.getJSONObject(i)
                        val reservationToAdd = Reservation(
                            res.getInt("id"),
                            res.getString("corso"),
                            res.getString("docente"),
                            State.getState(State.fromItaToNum(res.getString("stato"))),
                            Day.fromIta(res.getString("giorno")),
                            res.getInt("ora")
                        )
                        val target =
                            ReservationVM.reservations[State.fromItaToNum(res.getString("stato"))].value!!
                        if (!target.contains(reservationToAdd))
                            target.add(reservationToAdd)
                    }
                },
                {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_history),
                        Toast.LENGTH_LONG
                    ).show()
                })
        )
    }
}