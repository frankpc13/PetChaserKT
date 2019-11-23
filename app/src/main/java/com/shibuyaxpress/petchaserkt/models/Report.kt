package com.shibuyaxpress.petchaserkt.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Report(
    var _id: String? = "",
    var description: String? = "",
    var location: String? = "",
    var latitude: Double? = 0.0,
    var longitude: Double? = 0.0,
    var lastSeen: String? = "",
    var user: User? = null,
    var pet: Pet? = null
):Parcelable