package com.ium.easyreps.model

import com.ium.easyreps.util.Day
import com.ium.easyreps.util.State

class Reservation(
    var id: Int,
    var course: String,
    var teacher: String,
    var state: State,
    var day: Day,
    var startAt: Int
): Comparable<Reservation> {
    override fun toString(): String {
        return "Reservation(id=$id, course=$course, teacher=$teacher, state=$state, day=$day, startAt=$startAt)"
    }

    override fun compareTo(other: Reservation): Int {
        if (startAt != other.startAt)
            return startAt - other.startAt
        if (course != other.course)
            return course.compareTo(other.course)
        if (teacher != other.teacher)
            teacher.compareTo(other.teacher)
        return 0
    }
}