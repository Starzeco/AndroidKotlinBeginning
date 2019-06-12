package com.example.androidtvapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jablko1.setOnClickListener {
            big_photo.setImageResource(R.drawable.jablko)
        }
        jablko2.setOnClickListener {
            big_photo.setImageResource(R.drawable.jabuszko2)
        }
        jablko3.setOnClickListener {
            big_photo.setImageResource(R.drawable.jablko3)
        }
    }
}
