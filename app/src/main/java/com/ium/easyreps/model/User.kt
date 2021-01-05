package com.ium.easyreps.model

class User(
    var name: String = "Guest",
    var password: String = "",
    var isLogged: Boolean = false
) {
    override fun toString(): String {
        return "User(name='$name', password=$password, isLogged=$isLogged)"
    }
}