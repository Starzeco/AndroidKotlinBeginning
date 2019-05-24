package com.example.game

import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var springAnimX: SpringAnimation
    lateinit var springAnimY: SpringAnimation
    lateinit var sensorManager: SensorManager
    var mGyro: Sensor? = null

    var width: Int = 0
    var height :Int = 0

    var counter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        width = size.x
        height = size.y

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        mGyro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        springAnimX = findViewById<View>(R.id.imageView5).let { img ->
            SpringAnimation(img, DynamicAnimation.TRANSLATION_X, 0f)
        }
        springAnimY = findViewById<View>(R.id.imageView5).let { img ->
            SpringAnimation(img, DynamicAnimation.TRANSLATION_Y, 0f)
        }
        button.setOnClickListener {
            textView.text = counter.toString()
        }
        if(counter == 1000){
            // videoView.visibility = 1
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, mGyro, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        var xPosition = event!!.values[0]*100
        var yPosition = event.values[2]*100
        if(xPosition < -width/2 || xPosition > width/2 || yPosition < -height/2 || yPosition > height/2){
            counter++
        }else{
            val forceX = SpringForce(-xPosition)
                .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW)
            springAnimX.spring = forceX
            springAnimX.start()

            val forceY = SpringForce(yPosition)
                .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
                .setStiffness(SpringForce.STIFFNESS_LOW)
            springAnimY.spring = forceY
            springAnimY.start()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
