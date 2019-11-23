package com.shibuyaxpress.petchaserkt.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Breed(
    var _id: String? = "",
    var name: String? = "",
    var image: String? = ""
):Parcelable