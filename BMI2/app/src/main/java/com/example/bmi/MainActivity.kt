package com.example.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bmi.logic.BmiForKgCm
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bmi = BmiForKgCm(0,0)

        //countButton.isEnabled = false

        countButton.setOnClickListener {
            var result:Double
            try{
                bmi.height = HeightInput.text.toString().toInt()
                bmi.mass = MassInput.text.toString().toInt()
                result = bmi.countBmi()
            }catch(e: IllegalArgumentException){
                result = 0.0
            }

            score.text = "%.2f".format(result)
        }
        switch_activity.setOnClickListener{
            val aboutIntent =Intent(this, About::class.java)
            startActivity(aboutIntent)
        }

    }

}
