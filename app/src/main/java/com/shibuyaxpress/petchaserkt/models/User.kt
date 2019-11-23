package com.shibuyaxpress.petchaserkt.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var _id: String? = "",
    var username: String? = "",
    var image: String? = "",
    var person: Person? = null
):Parcelable