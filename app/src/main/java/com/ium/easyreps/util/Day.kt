package com.ium.easyreps.util

enum class Day {
    MON, TUE, WED, THU, FRI;

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

        fun getDayName(i: Day): String {
            return when (i) {
                MON -> "Monday"
                TUE -> "Tuesday"
                WED -> "Wednesday"
                THU -> "Thursday"
                FRI -> "Friday"
            }
        }

        fun toIta(day: Day): String {
            return when (day) {
                MON -> "LUN"
                TUE -> "MAR"
                WED -> "MER"
                THU -> "GIO"
                FRI -> "VEN"
            }
        }

        fun fromIta(day: String): Day {
            return when (day) {
                "LUN" -> MON
                "MAR" -> TUE
                "MER" -> WED
                "GIO" -> THU
                "VEN" -> FRI
                else -> throw IllegalArgumentException("Invalid day name")
            }
        }

        fun getDayNum(day: Day): Int {
            return when (day) {
                MON -> 0
                TUE -> 1
                WED -> 2
                THU -> 3
                FRI -> 4
            }
        }
    }
}