package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_info_bmi.*
import kotlinx.android.synthetic.main.activity_main.*

class InfoBMI : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_bmi)
        val bmiResult = intent.getStringExtra(getString(R.string.result_info))
        bmi_result_rl.text = bmiResult
        when(bmiResult.replace(',','.').toDouble()){
            in 0.1..18.4 -> {
                your_result.text = getString(R.string.underweight)
                bmi_result_rl.setTextColor(resources.getColor(R.color.grynszpan))
                your_result.setTextColor(resources.getColor(R.color.grynszpan))
            }
            in 18.5..24.9 -> {
                your_result.text = getString(R.string.normal_weight)
                bmi_result_rl.setTextColor(resources.getColor(R.color.colorPrimary))
                your_result.setTextColor(resources.getColor(R.color.colorPrimary))
            }
            in 25.0..29.9 -> {
                your_result.text = getString(R.string.over_weight)
                score.setTextColor(resources.getColor(R.color.pompeianRose))
                your_result.setTextColor(resources.getColor(R.color.pompeianRose))
            }
            in 30.0..34.9 -> {
                your_result.text = getString(R.string.obese_weight)
                bmi_result_rl.setTextColor(resources.getColor(R.color.LapisLazuli))
                your_result.setTextColor(resources.getColor(R.color.LapisLazuli))
            }
            in 35.0..Double.POSITIVE_INFINITY -> {
                your_result.text = getString(R.string.extremly_obese_weight)
                bmi_result_rl.setTextColor(resources.getColor(R.color.colorPrimaryDark))
                your_result.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            }
        }
        height_display.text= intent.getStringExtra(getString(R.string.height_info))
        mass_display.text = intent.getStringExtra(getString(R.string.mass_info))
    }
}
