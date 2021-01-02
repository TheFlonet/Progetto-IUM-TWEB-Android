package com.ium.easyreps.dto

class Course(var id: Int, var name: String) {
    override fun toString(): String {
        return "Course(id=$id, name='$name')"
    }
}