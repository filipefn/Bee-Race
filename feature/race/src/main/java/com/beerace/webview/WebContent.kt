package com.beerace.webview

internal sealed interface WebContent {

    data class Url(
        val url: String
    ) : WebContent

    data class Post(
        val url: String,
        val data: ByteArray
    ) : WebContent {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Post

            if (url != other.url) return false
            if (!data.contentEquals(other.data)) return false

            return true
        }

        override fun hashCode(): Int = 31 * url.hashCode() + data.contentHashCode()
    }

    object NavigatorOnly : WebContent
}