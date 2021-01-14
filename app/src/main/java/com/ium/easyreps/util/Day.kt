package com.ium.easyreps.util

enum class Day {
    MON, TUE, WED, THU, FRI;

    fun toIta(): String {
        return when (this) {
            MON -> "Lun"
            TUE -> "Mar"
            WED -> "Mer"
            THU -> "Gio"
            FRI -> "Ven"
        }
    }

    companion object {
        fun getDay(i: Int): Day {
            return when (i) {
                0 -> MON
                1 -> TUE
                2 -> WED
                3 -> THU
                4 -> FRI
                else -> throw IndexOutOfBoundsException("Invalid day index")
            }
        }

        fun getDayName(i: Int): String {
            return when (i) {
                0 -> "Monday"
                1 -> "Tuesday"
                2 -> "Wednesday"
                3 -> "Thursday"
                4 -> "Friday"
                else -> throw IndexOutOfBoundsException("Invalid day index")
            }
        }

        fun getDayName(i: Day): String {
            return when (i) {
                MON -> "Monday"
                TUE -> "Tuesday"
                WED -> "Wednesday"
                THU -> "Thursday"
                FRI -> "Friday"
            }
        }
    }
}