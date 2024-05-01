package com.example.dm_pract3.data

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

data class Plant(
    val name: String,
    val imageUrl: String,
    val description: String,
    val growZoneNumber: Int,
    val wateringInterval: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(imageUrl)
        parcel.writeString(description)
        parcel.writeInt(growZoneNumber)
        parcel.writeInt(wateringInterval)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Plant> {
        override fun createFromParcel(parcel: Parcel): Plant {
            return Plant(parcel)
        }

        override fun newArray(size: Int): Array<Plant?> {
            return arrayOfNulls(size)
        }
    }
}