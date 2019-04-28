package com.example.secondapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.secondapp.row_models.FirstRow
import kotlinx.android.synthetic.main.activity_with_fragments.*

class ActivityWithFragments : AppCompatActivity() {

    companion object {
        const val ROW_LIST = "rowList"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragments)

            textView.text = intent.getParcelableArrayListExtra<FirstRow>(ROW_LIST).size.toString()
    }

}
