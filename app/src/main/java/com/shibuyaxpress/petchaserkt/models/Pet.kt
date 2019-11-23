package com.shibuyaxpress.petchaserkt.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Pet (
    var _id: String? = "",
    var name: String? = "",
    var image: String? = "",
    var breed: Breed? = null
):Parcelable