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

        fun getStateName(i: Int): String{
            return when (i) {
                0 -> "Active"
                1 -> "Done"
                2 -> "Cancelled"
                else -> throw IndexOutOfBoundsException("Invalid state index")
            }
        }

        fun getStateName(i: State): String {
            return when (i) {
                ACTIVE -> "Active"
                DONE -> "Done"
                CANCELLED -> "Cancelled"
            }
        }
    }
}