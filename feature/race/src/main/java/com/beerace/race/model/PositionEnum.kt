package com.beerace.race.model

import com.beerace.race.R

internal enum class PositionEnum(val medal: Int?, val sufix: Int) {
    FIRST_PLACE(medal = R.drawable.first, sufix = R.string.sufix_first),
    SECOND_PLACE(medal = R.drawable.second, sufix = R.string.sufix_second_and_third),
    THIRD_PLACE(medal = R.drawable.third, sufix = R.string.sufix_second_and_third),
    ANOTHER(medal = null, sufix = R.string.sufix_another);

    companion object {
        fun getPositionEnum(position: Int): PositionEnum =
            when (position) {
                FIRST_PLACE.ordinal -> FIRST_PLACE
                SECOND_PLACE.ordinal -> SECOND_PLACE
                THIRD_PLACE.ordinal -> THIRD_PLACE
                else -> ANOTHER
            }
    }
}