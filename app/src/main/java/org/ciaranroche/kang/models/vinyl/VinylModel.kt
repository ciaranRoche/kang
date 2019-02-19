package org.ciaranroche.kang.models.vinyl

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.ciaranroche.kang.models.rating.RatingModel


@SuppressLint("ParcelCreator")
@Parcelize
data class VinylModel(var id: Long = 0,
                      var fbid: String = "",
                      var artist: String = "",
                      var name: String = "",
                      var desc: String = "",
                      var image: String = "",
                      var genre: String = "",
                      var rating: RatingModel = RatingModel()
) : Parcelable
