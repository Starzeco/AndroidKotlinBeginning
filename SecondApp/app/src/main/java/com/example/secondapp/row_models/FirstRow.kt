package com.example.secondapp.row_models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
class FirstRow(val name:String, val url: String, val date: String, val tags: ArrayList<String> = ArrayList()) : Parcelable {

    fun countFitness(otherRow: FirstRow): Int{
        var score= 0
        if(!otherRow.tags.isEmpty()){
            for(i in 0..2){
                if(tags.contains(otherRow.tags[i])){
                    score++
                }
            }
        }
        return score
    }
}