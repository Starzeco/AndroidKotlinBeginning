package com.example.bmi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.content.ContextCompat
import com.example.bmi.logic.Bmi
import com.example.bmi.logic.BmiForImperial
import com.example.bmi.logic.BmiForKgCm
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Double.POSITIVE_INFINITY
import java.text.SimpleDateFormat
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

    companion object {
        const val MASS_TEXT = "mass_text"
        const val HEIGHT_TEXT = "height_text"
        const val MASS_HINT = "mass_hint"
        const val HEIGHT_HINT= "height_hint"
        const val HEIGHT_LIST = "height_list"
        const val MASS_LIST = "mass_list"
        const val BMI_LIST = "bmi_list"
        const val DATE_LIST = "date_list"
        const val RESULT_INFO = "result_info"
        const val MASS_INFO = "mass_info"
        const val HEIGHT_INFO = "height_info"
        const val MASS_KG = "Mass [kg]"
        const val HEIGHT_CM = "Height [cm]"
        const val SCORE = "Score"
        const val SCORE_TEXT = "ScoreText"
        const val COLOR = "Color"
    }

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
                heightList.add(temp_height_list[i])
                massList.add(temp_mass_list[i])
                bmiList.add(temp_bmi_list[i])
                dateList.add(temp_date_list[i])
            }
        }
        if(textView2.text.toString() == getString(R.string.height_in)){
            bmi = BmiForImperial(0,0)
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
                if (bmi is BmiForKgCm) {
                    heightList.add(0, HeightInput.text.toString() + getString(R.string.cm))
                    massList.add(0, MassInput.text.toString() + getString(R.string.kg))
                } else {
                    heightList.add(0, HeightInput.text.toString() + getString(R.string.in_unit))
                    massList.add(0, MassInput.text.toString() + getString(R.string.lb))
                }
                bmiList.add(0, "%.2f".format(result))
                dateList.add(0, simpleDate.format(Date()))
                if(bmiList.size==11){
                    bmiList.removeAt(10)
                    dateList.removeAt(10)
                    heightList.removeAt(10)
                    massList.removeAt(10)
                }

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
                    score.setTextColor(ContextCompat.getColor(this,R.color.grynszpan))
                }
                in 18.5..24.99 -> {
                    term=getString(R.string.normal_weight)
                    score.setTextColor(ContextCompat.getColor(this,R.color.colorPrimary))
                }
                in 25.0..29.99 -> {
                    term=getString(R.string.over_weight)
                    score.setTextColor(ContextCompat.getColor(this,R.color.pompeianRose))
                }
                in 30.0..34.99 -> {
                    term=getString(R.string.obese_weight)
                    score.setTextColor(ContextCompat.getColor(this,R.color.LapisLazuli))
                }
                in 35.0..POSITIVE_INFINITY -> {
                    term=getString(R.string.extremly_obese_weight)
                    score.setTextColor(ContextCompat.getColor(this,R.color.colorPrimaryDark))
                }
            }
            if(result != 0.0){
                score.text = "%.2f".format(result)
                scoreText.text=term
            }

        }
        switch_activity.setOnClickListener{
            val infoIntent =Intent(this, InfoBMI::class.java)
            infoIntent.putExtra(RESULT_INFO, "%.2f".format(result))
            infoIntent.putExtra(HEIGHT_INFO, HeightInput.text.toString())
            infoIntent.putExtra(MASS_INFO, MassInput.text.toString())
            startActivity(infoIntent)
        }


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if(savedInstanceState != null){
            MassInput.setText(savedInstanceState.getString(MASS_KG))
            HeightInput.setText(savedInstanceState.getString(HEIGHT_CM))
            score.text = savedInstanceState.getString(SCORE)
            scoreText.text = savedInstanceState.getString(SCORE_TEXT)
            score.setTextColor(savedInstanceState.getInt(COLOR))

            mass.text = savedInstanceState.getString(MASS_TEXT)
            textView2.text = savedInstanceState.getString(HEIGHT_TEXT)
            MassInput.hint = savedInstanceState.getString(MASS_HINT)
            HeightInput.hint = savedInstanceState.getString(HEIGHT_HINT)

        }

    }



    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        if(outState != null){
            outState.run {
                putString(MASS_KG, MassInput.text.toString())
                putString(HEIGHT_CM, HeightInput.text.toString())
                putString(SCORE, score.text.toString())
                putString(SCORE_TEXT, scoreText.text.toString())
                putInt(COLOR, score.currentTextColor)

                putStringArrayList(HEIGHT_LIST, heightList)
                putStringArrayList(MASS_LIST, massList)
                putStringArrayList(BMI_LIST, bmiList)
                putStringArrayList(DATE_LIST, dateList)

                putString(MASS_TEXT, mass.text.toString())
                putString(HEIGHT_TEXT, textView2.text.toString())
                putString(MASS_HINT, MassInput.hint.toString())
                putString(HEIGHT_HINT, HeightInput.hint.toString())
            }
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
                HeightInput.hint = getString(R.string.provide_height_in)
                MassInput.hint = getString(R.string.provide_mass_lb)
            }else {
                if (bmi is BmiForImperial) {
                    bmi = BmiForKgCm(0, 0)
                    mass.text = getString(R.string.mass)
                    textView2.text = getString(R.string.height)
                    HeightInput.hint = getString(R.string.provide_height)
                    MassInput.hint = getString(R.string.provide_mass)
                }
            }
            HeightInput.setText("")
            MassInput.setText("")
            score.text=""
            scoreText.text=""
            return true
        }
        if(id == R.id.history_id){
            val historyIntent = Intent(this, History::class.java)
            historyIntent.putStringArrayListExtra(HEIGHT_LIST, heightList)
            historyIntent.putStringArrayListExtra(MASS_LIST, massList)
            historyIntent.putStringArrayListExtra(BMI_LIST, bmiList)
            historyIntent.putStringArrayListExtra(DATE_LIST, dateList)
            startActivity(historyIntent)
            return true
        }

        return super.onOptionsItemSelected(item)
    }

}
