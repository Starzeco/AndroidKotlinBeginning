package com.example.game

import android.graphics.Point
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var springAnimX: SpringAnimation
    lateinit var springAnimY: SpringAnimation
    lateinit var sensorManager: SensorManager
    var accel: Sensor? = null
    var light: Sensor? = null

    var width: Int = 0
    var height :Int = 0

    var counter: Int = 0

    lateinit var sound: MediaPlayer

    var currentX = 0f
    var currentY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sound = MediaPlayer.create(this, R.raw.egg)
        val backgroundSound = MediaPlayer.create(this, R.raw.pokemon)
        backgroundSound.isLooping = true
        backgroundSound.start()


        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        width = size.x
        height = size.y

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        springAnimX = findViewById<View>(R.id.imageView5).let { img ->
            SpringAnimation(img, DynamicAnimation.TRANSLATION_X, currentX)
        }
        springAnimY = findViewById<View>(R.id.imageView5).let { img ->
            SpringAnimation(img, DynamicAnimation.TRANSLATION_Y, currentY)
        }
        button.setOnClickListener {
            textView.text = counter.toString()
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            currentX += event.values[0]
            currentY += event.values[1]
            if (currentX < 0 || currentX > width || currentY < 0 || currentY > height) {
                counter++
                sound.start()
                springAnimY.animateToFinalPosition(0f)
                springAnimX.animateToFinalPosition(0f)
            }else{
                val forceX = SpringForce(-currentX)
                    .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
                    .setStiffness(SpringForce.STIFFNESS_LOW)
                springAnimX.spring = forceX
                springAnimX.start()

                val forceY = SpringForce(currentY)
                    .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
                    .setStiffness(SpringForce.STIFFNESS_LOW)
                springAnimY.spring = forceY
                springAnimY.start()
            }
        }else{
            if(event.values[0] < 500){
                if(counter > 100){
                    counter = 0
                    val random = Random.nextInt(10)
                    when(random){
                        0 -> {
                            imageView5.setImageResource(R.mipmap.custom_caterpie_foreground)
                            sound = MediaPlayer.create(this, R.raw.caterpie)
                        }
                        1 -> {
                            imageView5.setImageResource(R.mipmap.custom_charmander_foreground)
                            sound = MediaPlayer.create(this, R.raw.charmander)
                        }
                        2 -> {
                            imageView5.setImageResource(R.mipmap.custom_eevee_foreground)
                            sound = MediaPlayer.create(this, R.raw.eevee)
                        }
                        3 -> {
                            imageView5.setImageResource(R.mipmap.custom_egg_foreground)
                            sound = MediaPlayer.create(this, R.raw.egg)
                        }
                        4 -> {
                            imageView5.setImageResource(R.mipmap.custom_jigg_foreground)
                            sound = MediaPlayer.create(this, R.raw.jigg)
                        }
                        5 -> {
                            imageView5.setImageResource(R.mipmap.custom_mankey_foreground)
                            sound = MediaPlayer.create(this, R.raw.mankey)
                        }
                        6 -> {
                            imageView5.setImageResource(R.mipmap.custom_meowth_foreground)
                            sound = MediaPlayer.create(this, R.raw.meowth)
                        }
                        7 -> {
                            imageView5.setImageResource(R.mipmap.custom_pidgey_foreground)
                            sound = MediaPlayer.create(this, R.raw.pidgey)
                        }
                        8 -> {
                            imageView5.setImageResource(R.mipmap.custom_pikachu_foreground)
                            sound = MediaPlayer.create(this, R.raw.pikachu)
                        }
                        9 -> {
                            imageView5.setImageResource(R.mipmap.custom_psy_foreground)
                            sound = MediaPlayer.create(this, R.raw.psyduck)
                        }
                    }
                }else{
                    Toast.makeText(this, "You have to try more", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
