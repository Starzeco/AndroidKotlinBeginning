package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_info_bmi.*
import kotlinx.android.synthetic.main.activity_main.*

class InfoBMI : AppCompatActivity() {

    companion object {
        const val RESULT_INFO = "result_info"
        const val MASS_INFO = "mass_info"
        const val HEIGHT_INFO = "height_info"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_bmi)
        val bmiResult = intent.getStringExtra(RESULT_INFO)
        bmi_result_rl.text = bmiResult
        when(bmiResult.replace(',','.').toDouble()){
            in 0.1..18.49 -> {
                your_result.text = getString(R.string.underweight)
                bmi_result_rl.setTextColor(ContextCompat.getColor(this, R.color.grynszpan))
                your_result.setTextColor(ContextCompat.getColor(this, R.color.grynszpan))
            }
            in 18.5..24.99 -> {
                your_result.text = getString(R.string.normal_weight)
                bmi_result_rl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
                your_result.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary))
            }
            in 25.0..29.99 -> {
                your_result.text = getString(R.string.over_weight)
                bmi_result_rl.setTextColor(ContextCompat.getColor(this, R.color.pompeianRose))
                your_result.setTextColor(ContextCompat.getColor(this, R.color.pompeianRose))
            }
            in 30.0..34.99 -> {
                your_result.text = getString(R.string.obese_weight)
                bmi_result_rl.setTextColor(ContextCompat.getColor(this, R.color.LapisLazuli))
                your_result.setTextColor(ContextCompat.getColor(this, R.color.LapisLazuli))
            }
            in 35.0..Double.POSITIVE_INFINITY -> {
                your_result.text = getString(R.string.extremly_obese_weight)
                bmi_result_rl.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                your_result.setTextColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
            }
        }
        height_display.text= intent.getStringExtra(HEIGHT_INFO)
        mass_display.text = intent.getStringExtra(MASS_INFO)
    }
}
