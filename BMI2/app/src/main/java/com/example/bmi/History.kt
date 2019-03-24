package com.example.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class History : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val heightList = intent.getStringArrayListExtra(getString(R.string.height_list))
        val massList = intent.getStringArrayListExtra(getString(R.string.mass_list))
        val bmiList = intent.getStringArrayListExtra(getString(R.string.bmi_list))

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = CustomAdapter(heightList, massList, bmiList)
    }
}
