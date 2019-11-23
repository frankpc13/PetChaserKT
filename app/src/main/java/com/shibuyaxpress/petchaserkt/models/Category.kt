package com.shibuyaxpress.petchaserkt.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    var _id: String? = "",
    var name: String? = "",
    var description: String? = ""
): Parcelable