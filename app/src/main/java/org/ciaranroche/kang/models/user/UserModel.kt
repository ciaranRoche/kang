package org.ciaranroche.kang.models.user

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import org.ciaranroche.kang.models.genre.GenreModel
import org.ciaranroche.kang.models.vinyl.VinylModel

@SuppressLint("ParcelCreator")
@Parcelize
data class UserModel(
    var id: Long = 0,
    var fbid: String = "",
    var username: String = "",
    var email: String = "",
    var userImage: String = "",
    var age: Int = 0,
    var dob: String = "",
    var bio: String = "",
    var favGenre: GenreModel = GenreModel(),
    var favVinyl: List<VinylModel> = emptyList()
) : Parcelable