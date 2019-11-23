package com.shibuyaxpress.petchaserkt.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Reward(
    var _id: String? = "",
    var name: String? = "",
    var price: String? = "",
    var category: Category? = null,
    var image: String? = ""
):Parcelable