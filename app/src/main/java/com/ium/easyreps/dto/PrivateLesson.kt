package com.ium.easyreps.dto

import com.ium.easyreps.util.Day

class PrivateLesson(var course: Course, var teacher: Teacher, var day: Day, var startAt: Int) : Comparable<PrivateLesson> {
    override fun toString(): String {
        return "PrivateLesson(course=$course, teacher=$teacher, day=$day, startAt=$startAt)"
    }

    override fun compareTo(other: PrivateLesson): Int {
        if (day != other.day)
            return day.compareTo(other.day)
        if (startAt != other.startAt)
            return startAt.compareTo(other.startAt)
        if (course.name != other.course.name)
            return course.name.compareTo(other.course.name)
        if (teacher.surname != other.teacher.surname)
            return teacher.surname.compareTo(other.teacher.surname)
        if (teacher.surname == other.teacher.surname && teacher.name != other.teacher.name)
            return teacher.name.compareTo(other.teacher.name)

        return 0
    }
}