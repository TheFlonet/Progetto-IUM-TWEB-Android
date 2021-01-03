package com.ium.easyreps.model

class Course(var id: Int, var name: String) {
    override fun toString(): String {
        return "Course(id=$id, name='$name')"
    }
}