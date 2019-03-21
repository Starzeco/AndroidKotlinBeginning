package com.example.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.example.bmi.logic.BmiForKgCm
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Double.POSITIVE_INFINITY
import kotlin.IllegalArgumentException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bmi = BmiForKgCm(0,0)
        var term=getString(R.string.wrong_data)

        countButton.isEnabled=false

        HeightInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                countButton.isEnabled= !s.isNullOrEmpty() && !MassInput.text.isNullOrEmpty()
            }
        })
        MassInput.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                countButton.isEnabled= !s.isNullOrEmpty() && !HeightInput.text.isNullOrEmpty()
            }

        })


        countButton.setOnClickListener {
            var result:Double
            try{
                bmi.height = HeightInput.text.toString().toInt()
                bmi.mass = MassInput.text.toString().toInt()
                result = bmi.countBmi()
            }catch(e: IllegalArgumentException){
                result = 0.0
                if(HeightInput.text.toString().toInt() !in 100..200){
                    HeightInput.error = getString(R.string.height_restriction)
                    score.text = ""
                    scoreText.text = getString(R.string.wrong_data)
                }
                if(MassInput.text.toString().toInt() !in 20..150){
                    MassInput.error = getString(R.string.mass_restriction)
                    score.text= ""
                    scoreText.text = getString(R.string.wrong_data)
                }
            }
            when(result){
                in 0.1..18.4 -> {
                    term=getString(R.string.underweight)
                    score.setTextColor(resources.getColor(R.color.verdigris))
                }
                in 18.5..24.9 -> {
                    term=getString(R.string.normal_weight)
                    score.setTextColor(resources.getColor(R.color.colorPrimary))
                }
                in 25.0..29.9 -> {
                    term=getString(R.string.over_weight)
                    score.setTextColor(resources.getColor(R.color.pompeianRose))
                }
                in 30.0..34.9 -> {
                    term=getString(R.string.obese_weight)
                    score.setTextColor(resources.getColor(R.color.LapisLazuli))
                }
                in 35.0..POSITIVE_INFINITY -> {
                    term=getString(R.string.extremly_obese_weight)
                    score.setTextColor(resources.getColor(R.color.colorPrimaryDark))
                }
            }
            if(result != 0.0){
                score.text = "%.2f".format(result)
                scoreText.text=term
            }

        }
        switch_activity.setOnClickListener{
            val aboutIntent =Intent(this, About::class.java)
            startActivity(aboutIntent)
        }

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        MassInput.setText(savedInstanceState!!.getString(getString(R.string.mass)))
        HeightInput.setText(savedInstanceState!!.getString(getString(R.string.height)))
        score.text = savedInstanceState!!.getString(getString(R.string.score))
        scoreText.text = savedInstanceState!!.getString(getString(R.string.score_text))
        score.setTextColor(savedInstanceState!!.getInt(getString(R.string.color)))
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putString(getString(R.string.mass), MassInput.text.toString())
        outState!!.putString(getString(R.string.height), HeightInput.text.toString())
        outState!!.putString(getString(R.string.score), score.text.toString())
        outState!!.putString(getString(R.string.score_text), scoreText.text.toString())
        outState!!.putInt(getString(R.string.color), score.currentTextColor)
    }

}
