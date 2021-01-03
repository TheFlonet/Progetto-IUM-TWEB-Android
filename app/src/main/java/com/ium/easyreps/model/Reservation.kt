package com.ium.easyreps.model

import com.ium.easyreps.util.Day
import com.ium.easyreps.util.State

class Reservation(
    var id: Int,
    var course: Course,
    var teacher: Teacher,
    var user: User,
    var state: State,
    var day: Day,
    var startAt: Int,
    var createdAt: Double
) {
    override fun toString(): String {
        return "Reservation(id=$id, course=$course, teacher=$teacher, user=$user, state=$state, day=$day, startAt=$startAt, createdAt=$createdAt)"
    }
}