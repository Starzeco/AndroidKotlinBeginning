package com.example.bmi.logic

interface Bmi {
    fun countBmi():Double
    fun changeHeight(height: Int):Unit
    fun changeMass(mass: Int):Unit
}