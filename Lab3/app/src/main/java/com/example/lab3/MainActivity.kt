package com.example.lab3

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    var image: Bitmap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            pickImage()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }
    private fun pickImage(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/"
        startActivityForResult(intent, 101) //TODO 101 zmienic na stałą
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK && requestCode == 101){
            val selectedImage = data!!.data
            image = MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)

            image?.apply{
                contentIV.setImageBitmap(image)
                val vision = FirebaseVisionImage.fromBitmap(this)
                val labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler()
                labeler.processImage(vision)
                    .addOnSuccessListener {
                       Log.d("LAB",it.map{ it.text }.joinToString(" "))
                    }
                    .addOnFailureListener {
                        Log.wtf("LAB", it.message)
                    }

            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when(item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
