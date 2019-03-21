package com.example.bmi.logic

class BmiForKgCm(var mass: Int, var height: Int) : Bmi{

    override fun countBmi() : Double {
        println(height)
        println(mass)
        require(mass in 20..150 && height in 100..200) {"LUL"}
        return mass * 10000.0/ (height*height)
    }
}