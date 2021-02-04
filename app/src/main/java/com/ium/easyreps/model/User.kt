package com.ium.easyreps.model

class User(
    var name: String = "Guest",
    var password: String = "",
    var isLogged: Boolean = false,
    var isAdmin: Boolean = false
) {
    override fun toString(): String {
        return "User(name='$name', isLogged=$isLogged)"
    }
}