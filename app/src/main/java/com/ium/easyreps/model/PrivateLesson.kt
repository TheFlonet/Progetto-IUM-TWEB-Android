package com.ium.easyreps.model

import com.ium.easyreps.util.Day

class PrivateLesson(var course: String, var teacher: String, var day: Day, var startAt: Int) {
    override fun toString(): String {
        return "PrivateLesson(course=$course, teacher=$teacher, day=$day, startAt=$startAt)"
    }
}