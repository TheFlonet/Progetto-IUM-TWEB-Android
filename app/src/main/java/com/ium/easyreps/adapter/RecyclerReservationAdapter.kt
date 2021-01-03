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
import com.ium.easyreps.model.Reservation
import com.ium.easyreps.util.Day
import com.ium.easyreps.util.State

class RecyclerReservationAdapter(private var reservationList: List<Reservation>) :
    RecyclerView.Adapter<RecyclerReservationAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var course: TextView = itemView.findViewById(R.id.courseText)
        var teacher: TextView = itemView.findViewById(R.id.teacherText)
        var start: TextView = itemView.findViewById(R.id.startText)
        var day: TextView = itemView.findViewById(R.id.dayText)
        var layout: ConstraintLayout = itemView.findViewById(R.id.courseLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.history_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val reservation = reservationList[position]
        holder.course.text = reservation.course.name
        val teacherTxt = "${reservation.teacher.surname} ${reservation.teacher.name}"
        holder.teacher.text = teacherTxt
        val hourTxt = "${reservation.startAt}-${reservation.startAt + 1}"
        holder.start.text = hourTxt
        val dayName = when (reservation.day) {
            Day.MON -> "Monday"
            Day.TUE -> "Tuesday"
            Day.WEN -> "Wednesday"
            Day.THU -> "Thursday"
            Day.FRI -> "Friday"
        }
        holder.day.text = dayName
        holder.layout.setOnClickListener {
            if (reservation.state == State.ACTIVE)
                showConfirmDialog(it, reservation)
        }
    }

    private fun showConfirmDialog(view: View, reservation: Reservation) {
        val dayName = when (reservation.day) {
            Day.MON -> view.context.getString(R.string.monday)
            Day.TUE -> view.context.getString(R.string.tuesday)
            Day.WEN -> view.context.getString(R.string.wednesday)
            Day.THU -> view.context.getString(R.string.thursday)
            Day.FRI -> view.context.getString(R.string.friday)
        }

        AlertDialog.Builder(view.context).setTitle(view.context.getString(R.string.discard_reservation))
            .setMessage(
                view.context.getString(
                    R.string.discard_message,
                    reservation.course.name,
                    reservation.teacher.surname,
                    dayName,
                    reservation.startAt
                )
            )
            .setPositiveButton(view.context.getString(R.string.discard)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                Toast.makeText(
                    view.context,
                    view.context.getString(R.string.discard_confirmation),
                    Toast.LENGTH_LONG
                ).show()
            }
            .setNegativeButton(view.context.getString(R.string.cancel)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }.create().show()
    }

    override fun getItemCount(): Int {
        return reservationList.size
    }
}