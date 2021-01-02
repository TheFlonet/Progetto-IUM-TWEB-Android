package com.ium.easyreps.adapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ium.easyreps.R
import com.ium.easyreps.dto.PrivateLesson
import com.ium.easyreps.util.Day

class RecyclerLessonsAdapter(
    private var lessons: List<PrivateLesson>
) : RecyclerView.Adapter<RecyclerLessonsAdapter.ViewHolder>() {

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
        holder.layout.setOnClickListener {
            showConfirmDialog(it, lesson)
        }
    }

    private fun showConfirmDialog(
        view: View,
        lesson: PrivateLesson
    ) {
        val dayName = when (lesson.day) {
            Day.MON -> view.context.getString(R.string.monday)
            Day.TUE -> view.context.getString(R.string.tuesday)
            Day.WEN -> view.context.getString(R.string.wednesday)
            Day.THU -> view.context.getString(R.string.thursday)
            Day.FRI -> view.context.getString(R.string.friday)
        }

        AlertDialog.Builder(view.context).setTitle(view.context.getString(R.string.confirm_booking))
            .setMessage(
                view.context.getString(
                    R.string.dialog_message,
                    lesson.course.name,
                    lesson.teacher.surname,
                    dayName,
                    lesson.startAt
                )
            )
            .setPositiveButton(view.context.getString(R.string.confirm)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                Toast.makeText(
                    view.context,
                    view.context.getString(R.string.book_confirmation),
                    Toast.LENGTH_LONG
                ).show()
            }
            .setNegativeButton(view.context.getString(R.string.abort)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }.create().show()
    }

    override fun getItemCount(): Int {
        return lessons.size
    }
}