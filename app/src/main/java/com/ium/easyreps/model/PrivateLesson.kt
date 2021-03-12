package com.ium.easyreps.model

import com.ium.easyreps.util.Day

class PrivateLesson(var course: Course, var teacher: Teacher, var day: Day, var startAt: Int): Comparable<PrivateLesson> {
    override fun toString(): String {
        return "PrivateLesson(course=$course, teacher=$teacher, day=$day, startAt=$startAt)"
    }

    override fun compareTo(other: PrivateLesson): Int {
        if (startAt != other.startAt)
            return startAt - other.startAt
        if (course.name != other.course.name)
            return course.name.compareTo(other.course.name)
        if (teacher.surname != other.teacher.surname)
            teacher.surname.compareTo(other.teacher.surname)
        return 0
    }
}