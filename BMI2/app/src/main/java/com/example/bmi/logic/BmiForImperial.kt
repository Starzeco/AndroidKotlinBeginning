package com.example.bmi.logic

class BmiForImperial(var mass: Int, var height: Int): Bmi{
    override fun countBmi(): Double {
        require(mass in 40..300 && height in 40..80)
        return mass*703.0/(height*height)
    }

    override fun changeHeight(height: Int) {
        this.height=height
    }

    override fun changeMass(mass: Int) {
        this.mass=mass
    }

}