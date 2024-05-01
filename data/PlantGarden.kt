package com.example.dm_pract3.data

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

data class PlantGarden(
    val name: String,
    val imageUrl: String,
    val description: String,
    val growZoneNumber: Int,
    val wateringInterval: Int,
    val plantedDate: LocalDate
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        LocalDate.ofEpochDay(parcel.readLong())
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(imageUrl)
        parcel.writeString(description)
        parcel.writeInt(growZoneNumber)
        parcel.writeInt(wateringInterval)
        parcel.writeLong(plantedDate.toEpochDay())
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlantGarden> {
        override fun createFromParcel(parcel: Parcel): PlantGarden {
            return PlantGarden(parcel)
        }

        override fun newArray(size: Int): Array<PlantGarden?> {
            return arrayOfNulls(size)
        }
    }
}
