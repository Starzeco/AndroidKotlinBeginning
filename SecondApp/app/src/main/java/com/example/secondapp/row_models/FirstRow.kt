package com.example.secondapp.row_models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class FirstRow(val name:String, val url: String, val date: String, val tags: ArrayList<String>) : Parcelable {

}