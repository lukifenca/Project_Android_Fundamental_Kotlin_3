package com.lukitor.consumerapp.entity

import android.os.Parcel
import android.os.Parcelable

data class Favorite(
        var id: Int?= 0,
        var username: String? = null,
        var nama: String? = null,
        var imgURL: String?=null
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(username)
        parcel.writeString(nama)
        parcel.writeString(imgURL)
    }
    override fun describeContents(): Int {return 0}
    companion object CREATOR : Parcelable.Creator<Favorite> {
        override fun createFromParcel(parcel: Parcel): Favorite {return Favorite(parcel)}
        override fun newArray(size: Int): Array<Favorite?> {return arrayOfNulls(size)}
    }
}