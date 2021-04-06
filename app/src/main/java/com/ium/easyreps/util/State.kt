package com.ium.easyreps.util

enum class State {
    ACTIVE, DONE, CANCELLED;

    companion object {
        fun getState(i: Int): State {
            return when (i) {
                0 -> ACTIVE
                1 -> DONE
                2 -> CANCELLED
                else -> throw IndexOutOfBoundsException("Invalid state index")
            }
        }

        fun fromItaToNum(state: String): Int {
            return when (state) {
                "ATTIVA" -> 0
                "EFFETTUATA" -> 1
                "DISDETTA" -> 2
                else -> throw IllegalArgumentException("Invalid state name")
            }
        }

        fun toNum(state: State): Int {
            return when (state) {
                ACTIVE -> 0
                DONE -> 1
                CANCELLED -> 2
            }
        }
    }
}