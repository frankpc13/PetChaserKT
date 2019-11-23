package com.shibuyaxpress.petchaserkt.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Person(
    var _id: String? = "",
    var name: String? = "",
    var lastName: String? = "",
    var address: String? = "",
    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0,
    var documentNumber: Int? = 0
):Parcelable