package jpmc.team15.userapp.models

import android.os.Parcel
import android.os.Parcelable

data class Board (
    val category: String = "",
    val image: String = "",
    val date: String = "",
    val assignedTo: String = "",
    val qty: Int = 0

):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt()

    ) {
    }

    override fun writeToParcel(parcel: Parcel, p1: Int) {
        parcel.writeString(category)
        parcel.writeString(image)
        parcel.writeString(date)
        parcel.writeString(assignedTo)
        parcel.writeInt(qty)


    }


    override fun describeContents(): Int =0


    companion object CREATOR : Parcelable.Creator<Board> {
        override fun createFromParcel(parcel: Parcel): Board {
            return Board(parcel)
        }

        override fun newArray(size: Int): Array<Board?> {
            return arrayOfNulls(size)
        }
    }
}