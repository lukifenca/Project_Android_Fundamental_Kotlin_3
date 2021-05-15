package com.lukitor.projectandroidfundamentalkotlin2
import android.os.Parcel
import android.os.Parcelable

data class User(
        var username: String? = "", var nama: String? = "",
        var follower: String? = "", var following: String? = "", var repository: String? = "",
        var location: String? = "", var company: String? = "",
        var foto: String? ="https://avatars.githubusercontent.com/u/14813051?v=4"
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(nama)
        parcel.writeString(follower)
        parcel.writeString(following)
        parcel.writeString(repository)
        parcel.writeString(location)
        parcel.writeString(company)
        parcel.writeString(foto)
    }
    override fun describeContents(): Int {return 0}
    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {return User(parcel)}
        override fun newArray(size: Int): Array<User?> {return arrayOfNulls(size)}
    }
}