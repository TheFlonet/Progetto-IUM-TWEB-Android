package com.ium.easyreps.model

import com.ium.easyreps.util.Day
import com.ium.easyreps.util.State

class Reservation(
    var id: Int,
    var course: Course,
    var teacher: Teacher,
    var state: State,
    var day: Day,
    var startAt: Int
) {
    override fun toString(): String {
        return "Reservation(id=$id, course=$course, teacher=$teacher, state=$state, day=$day, startAt=$startAt)"
    }
}