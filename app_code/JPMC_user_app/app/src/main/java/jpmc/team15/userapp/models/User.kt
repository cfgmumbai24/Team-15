package jpmc.team15.userapp.models

import android.os.Parcel
import android.os.Parcelable

data class User(
    val id:String = "",
    val name: String = "",
    val email: String = "",
    val mobile_1: Long = 0,
    val mobile_2: Long = 0,

):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readLong(),
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(email)
        parcel.writeLong(mobile_1)
        parcel.writeLong(mobile_2)

    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}