package com.example.galeria

import android.widget.ImageView
import androidx.core.view.isInvisible
import java.lang.Thread.sleep

class CircleThread(var img: ImageView):Runnable {

    override fun run() {

        while (true){
            sleep(300L)
            img.isInvisible = !img.isInvisible

        }
    }
}