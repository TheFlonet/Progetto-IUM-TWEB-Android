package com.ium.easyreps.model

class Teacher(var id: Int, var name: String, var surname: String) {
    override fun toString(): String {
        return "Teacher(id=$id, name='$name', surname='$surname')"
    }
}
