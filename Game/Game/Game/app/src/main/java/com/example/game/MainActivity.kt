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
        const val CATERPIE = "caterpie"
        const val CHARMANDER = "charmander"
        const val PIKACHU = "pikachu"
        const val MEOWTH = "meowth"
        const val PSYDUCK = "psyduck"
        const val PIDGEY = "pidgey"
        const val JIGG = "jigg"
        const val MANKEY = "mankey"
        const val EEVEE = "eevee"
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
    var seenPokemons = ArrayList<String>()

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

        podpowiedz.text = getString(R.string.hit)
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
                        podpowiedz.text = getString(R.string.turn_off_light)
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
                if (event.values[0] < 5000) {
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
                                podpowiedz.text = getString(R.string.found_caterpie)
                                caterpieView.visibility = View.VISIBLE
                                addIfDontContains(CATERPIE)
                            }
                            1 -> {
                                imageView5.setImageResource(R.mipmap.custom_charmander_foreground)
                                sound = MediaPlayer.create(this, R.raw.charmander)
                                podpowiedz.text = getString(R.string.found_charmander)
                                charmView.visibility = View.VISIBLE
                                addIfDontContains(CHARMANDER)
                            }
                            2 -> {
                                imageView5.setImageResource(R.mipmap.custom_eevee_foreground)
                                sound = MediaPlayer.create(this, R.raw.eevee)
                                podpowiedz.text = getString(R.string.found_eevee)
                                eeveeView.visibility = View.VISIBLE
                                addIfDontContains(EEVEE)
                            }
                            3 -> {
                                imageView5.setImageResource(R.mipmap.custom_egg_foreground)
                                sound = MediaPlayer.create(this, R.raw.egg)
                                podpowiedz.text = getString(R.string.found_egg)
                            }
                            4 -> {
                                imageView5.setImageResource(R.mipmap.custom_jigg_foreground)
                                sound = MediaPlayer.create(this, R.raw.jigg)
                                podpowiedz.text = getString(R.string.found_jigg)
                                jiggView.visibility = View.VISIBLE
                                addIfDontContains(JIGG)
                            }
                            5 -> {
                                imageView5.setImageResource(R.mipmap.custom_mankey_foreground)
                                sound = MediaPlayer.create(this, R.raw.mankey)
                                podpowiedz.text = getString(R.string.found_mankey)
                                mankeyView.visibility = View.VISIBLE
                                addIfDontContains(MANKEY)
                            }
                            6 -> {
                                imageView5.setImageResource(R.mipmap.custom_meowth_foreground)
                                sound = MediaPlayer.create(this, R.raw.meowth)
                                podpowiedz.text = getString(R.string.found_meowth)
                                meowthView.visibility = View.VISIBLE
                                addIfDontContains(MEOWTH)
                            }
                            7 -> {
                                imageView5.setImageResource(R.mipmap.custom_pidgey_foreground)
                                sound = MediaPlayer.create(this, R.raw.pidgey)
                                podpowiedz.text = getString(R.string.found_pidgey)
                                pidgeyView.visibility = View.VISIBLE
                                addIfDontContains(PIDGEY)
                            }
                            8 -> {
                                imageView5.setImageResource(R.mipmap.custom_pikachu_foreground)
                                sound = MediaPlayer.create(this, R.raw.pikachu)
                                podpowiedz.text = getString(R.string.found_pikachu)
                                pikachuView.visibility = View.VISIBLE
                                addIfDontContains(PIKACHU)
                            }
                            9 -> {
                                imageView5.setImageResource(R.mipmap.custom_psy_foreground)
                                sound = MediaPlayer.create(this, R.raw.psyduck)
                                podpowiedz.text = getString(R.string.found_psyduck)
                                psyView.visibility = View.VISIBLE
                                addIfDontContains(PSYDUCK)
                            }
                        }
                        if(seenPokemons.size == 9){
                            podpowiedz.text = getString(R.string.game_over)
                            imageView5.setOnClickListener {
                                seenPokemons.clear()
                                setToInvisibleAll()
                                imageView5.setImageResource(R.mipmap.custom_egg_foreground)
                                imageView5.setOnClickListener(null)
                            }
                        }

                    } else {
                        Toast.makeText(this, getString(R.string.try_again), Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }
    fun addIfDontContains(name: String){
        if(!seenPokemons.contains(name)){
            seenPokemons.add(name)
        }
    }

    fun setToInvisibleAll(){
        psyView.visibility = View.INVISIBLE
        pikachuView.visibility = View.INVISIBLE
        pidgeyView.visibility = View.INVISIBLE
        meowthView.visibility = View.INVISIBLE
        mankeyView.visibility = View.INVISIBLE
        jiggView.visibility = View.INVISIBLE
        eeveeView.visibility = View.INVISIBLE
        charmView.visibility = View.INVISIBLE
        caterpieView.visibility = View.INVISIBLE
        seen.visibility = View.INVISIBLE
        firstGo = true
        podpowiedz.text = getString(R.string.hit)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}
