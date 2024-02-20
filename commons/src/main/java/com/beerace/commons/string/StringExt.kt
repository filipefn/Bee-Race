package com.beerace.commons.string

import androidx.compose.ui.graphics.Color
import androidx.core.graphics.toColorInt

const val BEGIN_RGBA_RED_INDEX = 0
const val END_RGBA_RED_INDEX = 2
const val END_RGBA_GREEN_INDEX = 4
const val END_RGBA_BLUE_INDEX = 6
const val MAX_SIZE_RGB = 6
const val END_RGBA_ALPHA_INDEX = 8

fun String?.toColor(): Color {
    val hex = this?.formatColorToConvert()

    return try {
        Color("#$hex".toColorInt())
    } catch (_: IllegalArgumentException) {
        Color.Transparent
    }
}

fun String.formatColorToConvert() =
    removePrefix("#").takeIf { it.length > MAX_SIZE_RGB }?.convertHexRGBAtoARGB() ?: removePrefix("#")

private fun String.convertHexRGBAtoARGB(): String {
    val red = this.substring(BEGIN_RGBA_RED_INDEX, END_RGBA_RED_INDEX)
    val green = this.substring(END_RGBA_RED_INDEX, END_RGBA_GREEN_INDEX)
    val blue = this.substring(END_RGBA_GREEN_INDEX, END_RGBA_BLUE_INDEX)
    val alpha = this.substring(END_RGBA_BLUE_INDEX, END_RGBA_ALPHA_INDEX)

    return "$alpha$red$green$blue"
}