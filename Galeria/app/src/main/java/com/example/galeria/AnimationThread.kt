package com.example.galeria

import android.content.Context
import android.widget.ImageView

class AnimationThread(var right: Boolean, var view: ImageView, var context: Context) : Runnable {
    override fun run() {
        if(right) {
            val list = listOf(
                R.drawable.zdjecia0600,
                R.drawable.zdjecia0601,
                R.drawable.zdjecia0602,
                R.drawable.zdjecia0603
            )
            for (el in list) {
                Thread.sleep(1000L)
                view.setImageResource(el)
            }
        }else{
            view.setImageResource(R.drawable.zdjecia0600)
        }
    }
/*
    private fun onSwipeRight() {
        //view.setImageResource(R.drawable.zdjecia0600)
    }

    private fun onSwipeLeft() {
        //klatki.setImageResource(drawable.zdjecia0836)
        *//*klatki.setImageDrawable(resources.getDrawable(2131100228))
        println("WTFFFF")
        Thread.sleep(1000L)

        for (i in 2131099749..2131100228 step 100){
            klatki.setBackgroundResource(i)
            Thread.sleep(10000L)
            println("YO")
        }*//*
    }*/
}