package com.example.finalnotesapp.model

import android.os.Parcel
import android.os.Parcelable

data class FavouritesModel(
    var favouriteId: String = "",
    var favouriteTitle: String = "",
    var favouriteDescription: String = "",
    var favouriteDate: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(favouriteId)
        parcel.writeString(favouriteTitle)
        parcel.writeString(favouriteDescription)
        parcel.writeString(favouriteDate)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<FavouritesModel> {
        override fun createFromParcel(parcel: Parcel): FavouritesModel {
            return FavouritesModel(parcel)
        }

        override fun newArray(size: Int): Array<FavouritesModel?> {
            return arrayOfNulls(size)
        }
    }
}
