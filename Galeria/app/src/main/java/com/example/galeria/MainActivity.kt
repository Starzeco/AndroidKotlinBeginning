package com.example.galeria

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs
import android.widget.PopupWindow
import android.widget.LinearLayout
import android.view.*
import kotlinx.android.synthetic.main.activity_main.view.*


class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener, CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var gestureDetector: GestureDetector
    private var imageNumber = 1

    companion object{
        private const val redText = "To jest czerwony obiekt"
        private const val greenText = "To jest zielony obiekt"
        private const val blueText = "To jest niebieski obiekt"
        private const val NUMBER_OF_IMAGES = 401
    }

    override fun onShowPress(e: MotionEvent?) {
    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {
        if(e != null){
            println(e.x)
            println(e.y)
            val pixel = getHotspotColor(e.x.toInt(), e.y.toInt())
            println("SPRAWDZAM")
            println(Color.RED)
            println(pixel)
            println(Color.red(Color.RED))
            println(Color.red(pixel))

            val inflanter: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val popup = inflanter.inflate(R.layout.activity_main, null)

            val width = LinearLayout.LayoutParams.WRAP_CONTENT
            val height = LinearLayout.LayoutParams.WRAP_CONTENT
            val focusable = true

            when {
                closeMatch(Color.RED, pixel, 25) -> {
                    popup.textView.text = redText
                    popup.textView.setTextColor(Color.BLACK)
                    popup.background = resources.getDrawable(R.drawable.zolty, theme)

                    val popupWindow = PopupWindow(popup, width, height, focusable)
                    popupWindow.showAtLocation(klatki, Gravity.CENTER, 0, 0)
                    popup.setOnClickListener {
                        popupWindow.dismiss()
                    }
                }
                closeMatch(Color.GREEN, pixel, 30) -> {
                    popup.textView.text = greenText
                    popup.textView.setTextColor(Color.BLACK)
                    popup.background = resources.getDrawable(R.drawable.zolty, theme)

                    val popupWindow = PopupWindow(popup, width, height, focusable)
                    popupWindow.showAtLocation(klatki, Gravity.CENTER, 0, 0)
                    popup.setOnClickListener {
                        popupWindow.dismiss()
                    }
                }
                closeMatch(Color.BLUE, pixel, 25) -> {
                    popup.textView.text = blueText
                    popup.textView.setTextColor(Color.BLACK)
                    popup.background = resources.getDrawable(R.drawable.zolty, theme)

                    val popupWindow = PopupWindow(popup, width, height, focusable)
                    popupWindow.showAtLocation(klatki, Gravity.CENTER, 0, 0)
                    popup.setOnClickListener {
                        popupWindow.dismiss()
                    }
                }
            }
            return true
        }
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
        return false
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
        var result=false
        if(e1 != null && e2 != null){
            val diffX = e1.x - e2.x
            val diffY = e1.y - e2.y
            if(abs(diffX) > abs(diffY)){
                if(diffX>0){
                    imageNumber += 1
                    if(imageNumber > NUMBER_OF_IMAGES){
                        imageNumber = 1
                    }
                    launch(Dispatchers.IO){
                        delay(130L * (imageNumber))
                        val id = resources.getIdentifier("k$imageNumber", "drawable", packageName)
                        val obraz = resources.getDrawable(id, theme)
                        launch(Dispatchers.Main) {
                            klatki.setImageDrawable(obraz)
                        }
                    }
                }else{
                    imageNumber -= 1
                    if(imageNumber <= 0){
                        imageNumber = NUMBER_OF_IMAGES
                    }
                    launch(Dispatchers.IO){
                        delay(130L * (imageNumber))
                        val id = resources.getIdentifier("k$imageNumber", "drawable", packageName)
                        val obraz = resources.getDrawable(id, theme)
                        launch(Dispatchers.Main) {
                            klatki.setImageDrawable(obraz)
                        }
                    }
                }
                result=true
            }
        }
        return result
    }

    override fun onLongPress(e: MotionEvent?) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        klatki.setImageResource(R.drawable.k1)

        gestureDetector = GestureDetector(this, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun getHotspotColor(x: Int, y: Int) : Int{
        val bitmap = (klatki.drawable as BitmapDrawable).bitmap
        return bitmap.getPixel(x, y)
    }

    private fun closeMatch(color1: Int, color2: Int, tolerance: Int): Boolean {
        if (abs(Color.red(color1) - Color.red(color2)) > tolerance)
            return false
        if (abs(Color.green(color1) - Color.green(color2)) > tolerance)
            return false
        return abs(Color.blue(color1) - Color.blue(color2)) <= tolerance
    }
}
