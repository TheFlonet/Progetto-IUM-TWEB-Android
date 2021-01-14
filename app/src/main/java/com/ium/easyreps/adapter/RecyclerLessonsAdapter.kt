package com.ium.easyreps.adapter

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ium.easyreps.R
import com.ium.easyreps.model.PrivateLesson
import com.ium.easyreps.util.Config
import com.ium.easyreps.util.Day
import com.ium.easyreps.util.NetworkUtil
import com.ium.easyreps.viewmodel.UserVM

class RecyclerLessonsAdapter(
    private var lessons: List<PrivateLesson>,
    private var isLogged: Boolean,
    private var username: String
) : RecyclerView.Adapter<RecyclerLessonsAdapter.ViewHolder>() {
    private var canBook = false

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var course: TextView = itemView.findViewById(R.id.courseText)
        var teacher: TextView = itemView.findViewById(R.id.teacherText)
        var start: TextView = itemView.findViewById(R.id.startText)
        var layout: ConstraintLayout = itemView.findViewById(R.id.courseLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.course_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lesson = lessons[position]
        holder.course.text = lesson.course.name
        val teacherTxt = "${lesson.teacher.surname} ${lesson.teacher.name}"
        holder.teacher.text = teacherTxt
        val hourTxt = "${lesson.startAt}-${lesson.startAt + 1}"
        holder.start.text = hourTxt
        if (isLogged)
            holder.layout.setOnClickListener {
                showConfirmDialog(it, lesson)
            }
        else
            holder.layout.setOnClickListener {
                Toast.makeText(
                    it.context,
                    it.context.getString(R.string.log_to_book),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    private fun showConfirmDialog(
        view: View,
        lesson: PrivateLesson
    ) {
        AlertDialog.Builder(view.context).setTitle(view.context.getString(R.string.confirm_booking))
            .setMessage(
                view.context.getString(
                    R.string.dialog_message,
                    lesson.course.name,
                    lesson.teacher.surname,
                    Day.getDayName(lesson.day),
                    lesson.startAt
                )
            )
            .setPositiveButton(view.context.getString(R.string.confirm)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                bookRequest(view.context, lesson)
                if (canBook) {
                    canBook = false
                    Toast.makeText(
                        view.context,
                        view.context.getString(R.string.book_confirmation),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        view.context,
                        view.context.getString(R.string.error_booking),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            .setNegativeButton(view.context.getString(R.string.abort)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }.create().show()
    }

    private fun bookRequest(context: Context, lesson: PrivateLesson) {
        if (NetworkUtil.checkConnection(context)) {
            val cancelRequest = JsonObjectRequest(
                Request.Method.POST,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.book}&username=${username}&idCorso=${lesson.course.id}&idDocente=${lesson.teacher.id}&ora=${lesson.startAt}&day=${lesson.day.toIta()}",
                null,
                {
                    canBook = true
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

    override fun getItemCount(): Int {
        return lessons.size
    }
}