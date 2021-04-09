package com.ium.easyreps.adapter

import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ium.easyreps.R
import com.ium.easyreps.model.Reservation
import com.ium.easyreps.util.Day
import com.ium.easyreps.util.ServerRequest
import com.ium.easyreps.util.State
import com.ium.easyreps.viewmodel.ReservationVM

class RecyclerReservationAdapter(private var reservations: ArrayList<Reservation>) :
    RecyclerView.Adapter<RecyclerReservationAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
        val reservation = reservations[position]
        holder.course.text = reservation.course
        val teacherTxt = reservation.teacher
        holder.teacher.text = teacherTxt
        val hourTxt = "${reservation.startAt}-${reservation.startAt + 1}"
        holder.start.text = hourTxt
        holder.day.text = Day.getDayName(reservation.day)
        holder.layout.setOnClickListener {
            changeState(it, reservation)
        }
    }

    private fun changeState(view: View, selectedReservation: Reservation) {
        if (selectedReservation.state == State.ACTIVE)
            showConfirmDialog(view, selectedReservation)
    }

    private fun showConfirmDialog(view: View, reservation: Reservation) =
        AlertDialog.Builder(view.context)
            .setTitle(view.context.getString(R.string.discard_reservation))
            .setMessage(
                view.context.getString(
                    R.string.discard_done,
                    reservation.course,
                    reservation.teacher,
                    Day.getDayName(reservation.day),
                    reservation.startAt
                )
            )
            .setPositiveButton(view.context.getString(R.string.discard)) { dialogInterface: DialogInterface, _: Int ->
                ServerRequest.cancelRequest(
                    view.context,
                    reservation
                ) { updateData(ReservationVM.reservations[State.toNum(reservation.state)].value!!) }
                dialogInterface.dismiss()
            }
            .setNeutralButton(view.context.getString(R.string.cancel)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }
            .setNegativeButton(view.context.getString(R.string.set_as_done)) { dialogInterface: DialogInterface, _: Int ->
                ServerRequest.doneRequest(
                    view.context,
                    reservation
                ) { updateData(ReservationVM.reservations[State.toNum(reservation.state)].value!!) }
                dialogInterface.dismiss()
            }.create().show()

    override fun getItemCount(): Int {
        return reservations.size
    }

    fun updateData(data: ArrayList<Reservation>) {
        reservations.clear()
        reservations.addAll(data)
        reservations.sort()
        notifyDataSetChanged()
    }
}