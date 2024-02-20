package com.beerace.commons.string

import android.content.Context
import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
sealed class StringResource : Parcelable {

    companion object {
        fun fromId(@StringRes id: Int, args: List<Any> = emptyList()): StringResource = IdResource(id, args)
    }

    internal data class TextResource(val text: String) : StringResource()

    internal data class IdResource(
        @StringRes val id: Int,
        val args: @RawValue List<Any> = emptyList()
    ) : StringResource()
}

fun StringResource.toString(context: Context): String {
    return when (this) {
        is StringResource.TextResource -> text
        is StringResource.IdResource -> when {
            id == -1 -> ""
            args.isEmpty() -> context.getString(id)
            else -> {
                val args: List<Any> = args.format(context)
                context.getString(id).format(args = args.toTypedArray())
            }
        }
    }
}

private fun List<Any>.format(context: Context) = this.map {
    if (it is StringResource) {
        it.toString(context)
    } else {
        it
    }
}