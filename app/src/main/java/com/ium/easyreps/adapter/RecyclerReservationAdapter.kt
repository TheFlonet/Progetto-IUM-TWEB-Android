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
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ium.easyreps.R
import com.ium.easyreps.model.Reservation
import com.ium.easyreps.util.Config
import com.ium.easyreps.util.Day
import com.ium.easyreps.util.NetworkUtil
import com.ium.easyreps.util.State

class RecyclerReservationAdapter(private var reservationList: List<Reservation>) :
    RecyclerView.Adapter<RecyclerReservationAdapter.ViewHolder>() {
    private var canCancel = false

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
        holder.day.text = Day.getDayName(reservation.day)
        holder.layout.setOnClickListener {
            if (reservation.state == State.ACTIVE)
                showConfirmDialog(it, reservation)
        }
    }

    private fun showConfirmDialog(view: View, reservation: Reservation) =
        AlertDialog.Builder(view.context)
            .setTitle(view.context.getString(R.string.discard_reservation))
            .setMessage(
                view.context.getString(
                    R.string.discard_message,
                    reservation.course.name,
                    reservation.teacher.surname,
                    Day.getDayName(reservation.day),
                    reservation.startAt
                )
            )
            .setPositiveButton(view.context.getString(R.string.discard)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                cancelRequest(view.context, reservation.id)
                if (canCancel) {
                    canCancel = false
                    Toast.makeText(
                        view.context,
                        view.context.getString(R.string.discard_confirmation),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        view.context,
                        view.context.getString(R.string.error_cancel),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
            .setNegativeButton(view.context.getString(R.string.cancel)) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
            }.create().show()

    private fun cancelRequest(context: Context, id: Int) {
        if (NetworkUtil.checkConnection(context)) {
            val cancelRequest = JsonObjectRequest(
                Request.Method.POST,
                "${Config.ip}:${Config.port}/${Config.servlet}?action=${Config.cancel}&id=$id",
                null,
                {
                    canCancel = true
                },
                {
                    Toast.makeText(
                        context,
                        context.getString(R.string.error_discard),
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
        return reservationList.size
    }
}