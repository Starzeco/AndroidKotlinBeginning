package com.example.bmi

import android.content.Context

class Preferences(context: Context) {
    val PREFERENCE_NAME ="First_preferences"
    val PREFERENCE_HEIGHT = "Height_List"
    val PREFERENCE_MASS = "Mass_Preference"
    val PREFERENCE_BMI = "Bmi_Preference"

    val preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

    fun getHeightList(): String = preferences.getString(PREFERENCE_HEIGHT, "")

    fun getMassList():String = preferences.getString(PREFERENCE_MASS, "")

    fun getBmiList():String = preferences.getString(PREFERENCE_BMI, "")

    fun setHeightList(list: String){
        val editor = preferences.edit()
        editor.putString(PREFERENCE_HEIGHT, list)
        editor.apply()
    }
    fun setMassList(list:String){
        val editor = preferences.edit()
        editor.putString(PREFERENCE_MASS, list)
        editor.apply()
    }
    fun setBmiList(list:String){
        val editor = preferences.edit()
        editor.putString(PREFERENCE_BMI, list)
        editor.apply()
    }
}