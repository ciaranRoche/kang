package org.ciaranroche.kang.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class VinylModel(var id: Long = 0,
                      var fbid: String = "",
                      var artist: String = "",
                      var name: String = "",
                      var desc: String = "",
                      var image: String = "",
                      var rating: RatingModel = RatingModel()) : Parcelable
