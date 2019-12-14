package com.example.galeria

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext
import android.view.*
import com.github.pwittchen.swipe.library.rx2.Swipe
import com.github.pwittchen.swipe.library.rx2.SwipeListener
import android.view.MotionEvent
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.drawable.toBitmap


class MainActivity : AppCompatActivity(), GestureDetector.OnGestureListener, CoroutineScope {
    private var job: Job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    private lateinit var gestureDetector: GestureDetector
    private var imageNumber = 1
    private val swipe = Swipe()

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

            val pixel = getHotspotColor(e.x.toInt(), e.y.toInt())

            val colors = arrayOf(Color.red(pixel), Color.green(pixel), Color.blue(pixel))
            val max = colors.max()
            val min = colors.min()

            val builder = AlertDialog.Builder(this)


            if(max!! - min!! > 10) {
                when (max) {
                    Color.red(pixel) -> {
                        builder.setTitle("Kolory")
                        builder.setMessage(redText)

                        builder.setPositiveButton("OK"){_,_ -> }

                        val dialog: AlertDialog = builder.create()
                        dialog.create()
                        dialog.show()
                    }
                    Color.blue(pixel) -> {
                        builder.setTitle("Kolory")
                        builder.setMessage(blueText)

                        builder.setPositiveButton("OK"){_,_ -> }

                        val dialog: AlertDialog = builder.create()
                        dialog.create()
                        dialog.show()
                    }
                    Color.green(pixel) -> {
                        builder.setTitle("Kolory")
                        builder.setMessage(greenText)

                        builder.setPositiveButton("OK"){_,_ -> }

                        val dialog: AlertDialog = builder.create()
                        dialog.create()
                        dialog.show()
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
        swipe.dispatchTouchEvent(event)
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
        klatki.setImageResource(R.drawable.k1)

        gestureDetector = GestureDetector(this, this)

        swipe.setListener(object : SwipeListener {
            override fun onSwipingLeft(event: MotionEvent) {
               imageNumber += 2
               if (imageNumber > NUMBER_OF_IMAGES) {
                   imageNumber = 1
               }
                val id = resources.getIdentifier("k$imageNumber", "drawable", packageName)
                val obraz = resources.getDrawable(id, theme)
                klatki.setImageDrawable(obraz)
            }

            override fun onSwipedLeft(event: MotionEvent): Boolean {
                return false
            }

            override fun onSwipingRight(event: MotionEvent) {
                imageNumber -= 2
                if (imageNumber <= 0) {
                    imageNumber = NUMBER_OF_IMAGES
                }
                val id = resources.getIdentifier("k$imageNumber", "drawable", packageName)
                val obraz = resources.getDrawable(id, theme)
                klatki.setImageDrawable(obraz)
            }

            override fun onSwipedRight(event: MotionEvent): Boolean {
                //info.setText("SWIPED_RIGHT")
                return false
            }

            override fun onSwipingUp(event: MotionEvent) {
                //info.setText("SWIPING_UP")
            }

            override fun onSwipedUp(event: MotionEvent): Boolean {
                //info.setText("SWIPED_UP")
                return false
            }

            override fun onSwipingDown(event: MotionEvent) {
                //info.setText("SWIPING_DOWN")
            }

            override fun onSwipedDown(event: MotionEvent): Boolean {
                //info.setText("SWIPED_DOWN")
                return false
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    private fun getHotspotColor(x: Int, y: Int) : Int{
        val id = resources.getIdentifier("k$imageNumber", "drawable", packageName)
        val obraz = resources.getDrawable(id, theme)
        val bitmap:Bitmap = obraz.toBitmap(1920, 1080)
        return bitmap.getPixel(x, y)
    }
}
