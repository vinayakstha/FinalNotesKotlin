package com.example.finalnotesapp.model

import android.os.Parcel
import android.os.Parcelable

data class NotesModel(
    var notesId: String = "",
    var notesTitle: String = "",
    var notesDescription: String = "",
    var notesDate: String = "",
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readString()?:"",
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(notesId)
        parcel.writeString(notesTitle)
        parcel.writeString(notesDescription)
        parcel.writeString(notesDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NotesModel> {
        override fun createFromParcel(parcel: Parcel): NotesModel {
            return NotesModel(parcel)
        }

        override fun newArray(size: Int): Array<NotesModel?> {
            return arrayOfNulls(size)
        }
    }
}

