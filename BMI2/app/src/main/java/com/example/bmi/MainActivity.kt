package com.example.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.example.bmi.logic.Bmi
import com.example.bmi.logic.BmiForImperial
import com.example.bmi.logic.BmiForKgCm
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Double.POSITIVE_INFINITY
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.IllegalArgumentException
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var bmi: Bmi? = BmiForKgCm(0,0)
    val heightList = ArrayList<String>()
    val massList = ArrayList<String>()
    val bmiList = ArrayList<String>()
    val dateList = ArrayList<String>()
    val gson = Gson()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

        val preferences = Preferences(this)

        var term=getString(R.string.wrong_data)
        var result = 0.0
        val type = object: TypeToken<ArrayList<String>>() {}.type
        val temp_height_list = gson.fromJson<ArrayList<String>>(preferences.getHeightList(), type)
        val temp_mass_list = gson.fromJson<ArrayList<String>>(preferences.getMassList(), type)
        val temp_bmi_list = gson.fromJson<ArrayList<String>>(preferences.getBmiList(), type)
        val temp_date_list = gson.fromJson<ArrayList<String>>(preferences.getDateList(), type)
        if(temp_bmi_list != null){
            val size = temp_bmi_list.size -1

            for(i in 0..size){
                heightList.add(0,temp_height_list[i])
                massList.add(0,temp_mass_list[i])
                bmiList.add(0,temp_bmi_list[i])
                dateList.add(0,temp_date_list[i])
            }
        }


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
            try{
                bmi?.apply {
                    changeHeight(HeightInput.text.toString().toInt())
                    changeMass(MassInput.text.toString().toInt())

                }
                result = bmi?.countBmi() ?: 0.0
                if(bmi is BmiForKgCm){
                    heightList.add(0,HeightInput.text.toString()+getString(R.string.cm))
                    massList.add(0,MassInput.text.toString()+getString(R.string.kg))
                }else{
                    heightList.add(0,HeightInput.text.toString()+getString(R.string.in_unit))
                    massList.add(0,MassInput.text.toString()+ getString(R.string.lb))
                }
                bmiList.add(0,"%.2f".format(result))
                dateList.add(0,simpleDate.format(Date()))
            }catch(e: IllegalArgumentException){
                try {
                    if (bmi is BmiForKgCm) {
                        if (HeightInput.text.toString().toInt() !in 100..200) {
                            HeightInput.error = getString(R.string.height_restriction)
                            score.text = ""
                            scoreText.text = getString(R.string.wrong_data)
                        }
                        if (MassInput.text.toString().toInt() !in 20..150) {
                            MassInput.error = getString(R.string.mass_restriction)
                            score.text = ""
                            scoreText.text = getString(R.string.wrong_data)
                        }
                    }
                    if (bmi is BmiForImperial) {
                        if (HeightInput.text.toString().toInt() !in 40..80) {
                            HeightInput.error = getString(R.string.height_restriction_imp)
                            score.text = ""
                            scoreText.text = getString(R.string.wrong_data)
                        }
                        if (MassInput.text.toString().toInt() !in 40..300) {
                            MassInput.error = getString(R.string.mass_restriction_imp)
                            score.text = ""
                            scoreText.text = getString(R.string.wrong_data)
                        }
                    }
                }catch(ex: NumberFormatException){
                    scoreText.text = getString(R.string.wrong_data)
                    MassInput.setText("")
                    HeightInput.setText("")
                }

            }
            when(result){
                in 0.1..18.49 -> {
                    term=getString(R.string.underweight)
                    score.setTextColor(resources.getColor(R.color.grynszpan))
                }
                in 18.5..24.99 -> {
                    term=getString(R.string.normal_weight)
                    score.setTextColor(resources.getColor(R.color.colorPrimary))
                }
                in 25.0..29.99 -> {
                    term=getString(R.string.over_weight)
                    score.setTextColor(resources.getColor(R.color.pompeianRose))
                }
                in 30.0..34.99 -> {
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
            val infoIntent =Intent(this, InfoBMI::class.java)
            infoIntent.putExtra(getString(R.string.result_info), "%.2f".format(result))
            infoIntent.putExtra(getString(R.string.height_info), HeightInput.text.toString())
            infoIntent.putExtra(getString(R.string.mass_info), MassInput.text.toString())
            startActivity(infoIntent)
        }


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState != null){
            MassInput.setText(savedInstanceState.getString(getString(R.string.mass)))
            HeightInput.setText(savedInstanceState.getString(getString(R.string.height)))
            score.text = savedInstanceState.getString(getString(R.string.score))
            scoreText.text = savedInstanceState.getString(getString(R.string.score_text))
            score.setTextColor(savedInstanceState.getInt(getString(R.string.color)))
            val temp_heigtList = savedInstanceState.getStringArrayList(getString(R.string.height_list))
            val temp_massList = savedInstanceState.getStringArrayList(getString(R.string.mass_list))
            val temp_bmiList = savedInstanceState.getStringArrayList(getString(R.string.bmi_list))
            val temp_dateList = savedInstanceState.getStringArrayList(getString(R.string.date_list))
            val size = temp_bmiList.size-1
            for (i in 0..size){
                bmiList.add(0,temp_bmiList[i])
                heightList.add(0,temp_heigtList[i])
                massList.add(0,temp_massList[i])
                dateList.add(0,temp_dateList[i])
            }
        }

    }



    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if(outState != null){
            outState.putString(getString(R.string.mass), MassInput.text.toString())
            outState.putString(getString(R.string.height), HeightInput.text.toString())
            outState.putString(getString(R.string.score), score.text.toString())
            outState.putString(getString(R.string.score_text), scoreText.text.toString())
            outState.putInt(getString(R.string.color), score.currentTextColor)
            outState.putStringArrayList(getString(R.string.height_list), heightList)
            outState.putStringArrayList(getString(R.string.mass_list), massList)
            outState.putStringArrayList(getString(R.string.bmi_list), bmiList)
            outState.putStringArrayList(getString(R.string.date_list), dateList)
        }
        val preferences = Preferences(this)
        val Height_json = gson.toJson(heightList)
        val Mass_json = gson.toJson(massList)
        val Bmi_json = gson.toJson(bmiList)
        val Date_json = gson.toJson(dateList)
        preferences.setDateList(Date_json)
        preferences.setHeightList(Height_json)
        preferences.setMassList(Mass_json)
        preferences.setBmiList(Bmi_json)


    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean{
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if(id == R.id.about_id){
            val aboutIntent = Intent(this, About::class.java)
            startActivity(aboutIntent)
            return true
        }
        if(id == R.id.change_id){
            if(bmi is BmiForKgCm){
                bmi=BmiForImperial(0,0)
                mass.text = getString(R.string.mass_lb)
                textView2.text = getString(R.string.height_in)
                HeightInput.setText("")
                MassInput.setText("")
                HeightInput.hint = getString(R.string.provide_height_in)
                MassInput.hint = getString(R.string.provide_mass_lb)
            }else {
                if (bmi is BmiForImperial) {
                    bmi = BmiForKgCm(0, 0)
                    mass.text = getString(R.string.mass)
                    textView2.text = getString(R.string.height)
                    HeightInput.setText("")
                    MassInput.setText("")
                    HeightInput.hint = getString(R.string.provide_height)
                    MassInput.hint = getString(R.string.provide_mass)
                }
            }
            return true
        }
        if(id == R.id.history_id){
            val historyIntent = Intent(this, History::class.java)
            historyIntent.putStringArrayListExtra(getString(R.string.height_list), heightList)
            historyIntent.putStringArrayListExtra(getString(R.string.mass_list), massList)
            historyIntent.putStringArrayListExtra(getString(R.string.bmi_list), bmiList)
            historyIntent.putStringArrayListExtra(getString(R.string.date_list), dateList)
            startActivity(historyIntent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
