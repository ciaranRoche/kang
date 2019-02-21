package org.ciaranroche.kang.models.genre

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class GenreModel(
    var title: String = "",
    var about: String = "",
    var color: Int = 0
) : Parcelable