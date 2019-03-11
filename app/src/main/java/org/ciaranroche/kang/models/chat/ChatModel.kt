package org.ciaranroche.kang.models.chat

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class ChatModel(
    var message: String = "",
    var user: String = "",
    var time: Long = 0L
) : Parcelable