package com.example.secondapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imageList = ArrayList<String>()
        val nameList = ArrayList<String>()
        val dateList = ArrayList<String>()
        val tagList = ArrayList<String>()

        val simpleDate = SimpleDateFormat("dd/M/yyyy hh:mm:ss")

        imageList.add("wtf")
        nameList.add("Boniek")
        dateList.add(simpleDate.format(Date()))
        tagList.add("cosTam")

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = CustomAdapter(imageList, nameList, dateList, tagList)
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean{
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId

        if(id == R.id.add){

            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
