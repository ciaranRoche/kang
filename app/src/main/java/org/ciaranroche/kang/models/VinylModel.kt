package org.ciaranroche.kang.models

import android.annotation.SuppressLint
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@SuppressLint("ParcelCreator")
@Parcelize
data class VinylModel(var id: Long = 0, var name: String = "", var desc: String = "") : Parcelable
