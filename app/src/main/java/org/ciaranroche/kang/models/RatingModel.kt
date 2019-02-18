package org.ciaranroche.kang.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@SuppressLint("ParcelCreator")
@Parcelize
data class RatingModel(var fivestar: Int = 0,
                       var fourstar: Int = 0,
                       var threestar: Int = 0,
                       var twostar: Int = 0,
                       var onestar: Int = 0,
                       var nostar: Int = 0) : Parcelable