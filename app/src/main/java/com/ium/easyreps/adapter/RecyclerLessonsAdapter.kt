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
import com.ium.easyreps.model.PrivateLesson
import com.ium.easyreps.util.Day
import com.ium.easyreps.util.ServerRequest
import com.ium.easyreps.viewmodel.CoursesVM
import com.ium.easyreps.viewmodel.UserVM

class RecyclerLessonsAdapter(
    var lessons: ArrayList<PrivateLesson>
) : RecyclerView.Adapter<RecyclerLessonsAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        val teacherTxt = lesson.teacher.surname + " " + lesson.teacher.name
        holder.teacher.text = teacherTxt
        val hourTxt = "${lesson.startAt}-${lesson.startAt + 1}"
        holder.start.text = hourTxt
        if (UserVM.user.value!!.isLogged)
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
        view: View, lesson: PrivateLesson
    ) = AlertDialog.Builder(view.context).setTitle(view.context.getString(R.string.confirm_booking))
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
            ServerRequest.bookRequest(view.context, lesson) {updateData(CoursesVM.courses[Day.getDayNum(lesson.day)].value!!)}
            dialogInterface.dismiss()
        }
        .setNegativeButton(view.context.getString(R.string.abort)) { dialogInterface: DialogInterface, _: Int ->
            dialogInterface.dismiss()
        }.create().show()

    override fun getItemCount(): Int {
        return lessons.size
    }

    fun updateData(data: ArrayList<PrivateLesson>) {
        lessons.clear()
        lessons.addAll(data)
        lessons.sort()
        notifyDataSetChanged()
    }
}