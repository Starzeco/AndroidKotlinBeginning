package com.example.galeria

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.util.*
import kotlin.concurrent.schedule
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs

class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener, CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var gestureDetector: GestureDetector

    override fun onShowPress(e: MotionEvent?) {

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        return false
    }

    override fun onDown(e: MotionEvent?): Boolean {
        return false
    }

    override fun onFling(
        downEvent: MotionEvent?,
        moveEvent: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        var result = false
        if(downEvent != null && moveEvent != null){
            val diffX = moveEvent.x - downEvent.x
            val diffY = moveEvent.y - downEvent.y
            if(abs(diffX) > abs(diffY)){
                if(abs(diffX) > 100 && abs(velocityX) > 100){
                    if(diffX > 0){
                        //val animation = Thread(AnimationThread(true, klatki, this))
                        //animation.start()

                        val list1 = listOf(
                            0,
                            1,
                            2,
                            3
                        )
                        list1.forEach { index ->
                            run {
                                launch(Dispatchers.Main) {
                                    val list2 = listOf(
                                        R.drawable.zdjecia0600,
                                        R.drawable.zdjecia0601,
                                        R.drawable.zdjecia0602,
                                        R.drawable.zdjecia0603
                                    )
                                    delay(10000)
                                    println("YOO")
                                    klatki.setImageResource(list2[index])
                                }
                            }
                        }
                    }else{
                        klatki.setImageResource(R.drawable.zdjecia0600)
                        //val animation = Thread(AnimationThread(false, klatki, this))
                        //animation.start()
                    }
                    result = true
                }
            }
        }
        return result
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        gestureDetector.onTouchEvent(event)

        return super.onTouchEvent(event)
    }

    override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent?,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        return false
    }

    override fun onLongPress(e: MotionEvent?) {
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        gestureDetector = GestureDetector(this, this)
    }
    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}
