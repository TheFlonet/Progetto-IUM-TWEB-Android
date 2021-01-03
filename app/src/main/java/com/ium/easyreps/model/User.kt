package com.ium.easyreps.model

class User(
    var id: Int = -1,
    var name: String = "Guest",
    var password: String = "",
    var isLogged: Boolean = false,
    var isAdmin: Boolean = false
) {
    override fun toString(): String {
        return "User(id=$id, name='$name', password=$password, isLogged=$isLogged, isAdmin=$isAdmin)"
    }
}