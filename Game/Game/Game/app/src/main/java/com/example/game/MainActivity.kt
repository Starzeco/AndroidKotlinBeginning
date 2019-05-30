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
    companion object {
        const val TURN_OFF_LIGHT = "Spróbuj zgasić świtało"
        const val TRY_AGAIN = "Próbuj dalej"
        const val FOUND_PIKACHU = "Znalazłeś pikachu"
        const val FOUND_PSYDUCK = "Znalazłeś psyduck'a"
        const val FOUND_CHARM = "Znalazłeś charmander'a"
        const val FOUND_MEOWTH = "Znalazłeś meowth'a"
        const val FOUND_CATERPIE = "Znalazłeś caterpie"
        const val FOUND_PIDGEY = "Znalazłeś pdigey'a"
        const val FOUND_JIGG = "Znalazłeś jigglypuff'a"
        const val FOUND_MANKEY = "Znalazłeś mankey'a"
        const val FOUND_EEVEE = "Znalazłeś eevee"
        const val FOUND_EGG = "Znalazłeś jajko"
        const val HIT = "Uderz o krawędź"
    }

    lateinit var springAnimX: SpringAnimation
    lateinit var springAnimY: SpringAnimation
    lateinit var sensorManager: SensorManager
    var accel: Sensor? = null
    var light: Sensor? = null
    var firstGo = true

    var width: Int = 0
    var height :Int = 0

    var counter: Int = 0

    lateinit var sound: MediaPlayer
    lateinit var backgroundSound: MediaPlayer

    var currentX = 0f
    var currentY = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sound = MediaPlayer.create(this, R.raw.egg)
        backgroundSound = MediaPlayer.create(this, R.raw.pokemon)
        backgroundSound.start()
        backgroundSound.isLooping = true

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        width = size.x
        height = size.y

        podpowiedz.text = HIT
        podpowiedz.textSize = 24f

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        light = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME)
        sensorManager.registerListener(this, light, SensorManager.SENSOR_DELAY_GAME)

        springAnimX = findViewById<View>(R.id.imageView5).let { img ->
            SpringAnimation(img, DynamicAnimation.TRANSLATION_X, currentX)
        }
        springAnimY = findViewById<View>(R.id.imageView5).let { img ->
            SpringAnimation(img, DynamicAnimation.TRANSLATION_Y, currentY)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event != null) {
            if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                currentX += event.values[0]*3
                currentY += event.values[1]*3
                if (currentX < -width / 2 || currentX > width / 2 || currentY < -height / 2 || currentY > height / 2) {
                    counter++
                    sound.start()
                    currentX = 0f
                    currentY = 0f
                    springAnimY.animateToFinalPosition(0f)
                    springAnimX.animateToFinalPosition(0f)
                    springAnimX.start()
                    springAnimY.start()
                    if(counter >=10){
                        podpowiedz.text = TURN_OFF_LIGHT
                    }
                } else {
                    val forceX = SpringForce(-currentX)
                        .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
                        .setStiffness(SpringForce.STIFFNESS_MEDIUM)
                    springAnimX.spring = forceX
                    springAnimX.start()

                    val forceY = SpringForce(currentY)
                        .setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY)
                        .setStiffness(SpringForce.STIFFNESS_MEDIUM)
                    springAnimY.spring = forceY
                    springAnimY.start()
                }
            } else {
                if (event.values[0] < 1000) {
                    if (counter > Random.nextInt(10, 30)) {
                        if(firstGo){
                            seen.visibility = View.VISIBLE
                            firstGo=false
                        }
                        counter = 0
                        podpowiedz.text = ""
                        if(!backgroundSound.isPlaying) backgroundSound.start()
                        val random = Random.nextInt(10)
                        when (random) {
                            0 -> {
                                imageView5.setImageResource(R.mipmap.custom_caterpie_foreground)
                                sound = MediaPlayer.create(this, R.raw.caterpie)
                                podpowiedz.text = FOUND_CATERPIE
                                caterpieView.visibility = View.VISIBLE
                            }
                            1 -> {
                                imageView5.setImageResource(R.mipmap.custom_charmander_foreground)
                                sound = MediaPlayer.create(this, R.raw.charmander)
                                podpowiedz.text = FOUND_CHARM
                                charmView.visibility = View.VISIBLE
                            }
                            2 -> {
                                imageView5.setImageResource(R.mipmap.custom_eevee_foreground)
                                sound = MediaPlayer.create(this, R.raw.eevee)
                                podpowiedz.text = FOUND_EEVEE
                                eeveeView.visibility = View.VISIBLE
                            }
                            3 -> {
                                imageView5.setImageResource(R.mipmap.custom_egg_foreground)
                                sound = MediaPlayer.create(this, R.raw.egg)
                                podpowiedz.text = FOUND_EGG
                            }
                            4 -> {
                                imageView5.setImageResource(R.mipmap.custom_jigg_foreground)
                                sound = MediaPlayer.create(this, R.raw.jigg)
                                podpowiedz.text = FOUND_JIGG
                                jiggView.visibility = View.VISIBLE
                            }
                            5 -> {
                                imageView5.setImageResource(R.mipmap.custom_mankey_foreground)
                                sound = MediaPlayer.create(this, R.raw.mankey)
                                podpowiedz.text = FOUND_MANKEY
                                mankeyView.visibility = View.VISIBLE
                            }
                            6 -> {
                                imageView5.setImageResource(R.mipmap.custom_meowth_foreground)
                                sound = MediaPlayer.create(this, R.raw.meowth)
                                podpowiedz.text = FOUND_MEOWTH
                                meowthView.visibility = View.VISIBLE
                            }
                            7 -> {
                                imageView5.setImageResource(R.mipmap.custom_pidgey_foreground)
                                sound = MediaPlayer.create(this, R.raw.pidgey)
                                podpowiedz.text = FOUND_PIDGEY
                                pidgeyView.visibility = View.VISIBLE
                            }
                            8 -> {
                                imageView5.setImageResource(R.mipmap.custom_pikachu_foreground)
                                sound = MediaPlayer.create(this, R.raw.pikachu)
                                podpowiedz.text = FOUND_PIKACHU
                                pikachuView.visibility = View.VISIBLE
                            }
                            9 -> {
                                imageView5.setImageResource(R.mipmap.custom_psy_foreground)
                                sound = MediaPlayer.create(this, R.raw.psyduck)
                                podpowiedz.text = FOUND_PSYDUCK
                                psyView.visibility = View.VISIBLE
                            }
                        }
                    } else {
                        Toast.makeText(this, TRY_AGAIN, Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
