package com.beerace.domain.statusrace

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class StatusRace(
    val name: String,
    val color: String
) : Parcelable