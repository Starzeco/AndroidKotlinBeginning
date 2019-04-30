package com.example.secondapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.secondapp.fragments.PhotoFragment
import com.example.secondapp.row_models.FirstRow
import kotlinx.android.synthetic.main.activity_with_fragments.*

class ActivityWithFragments : AppCompatActivity() {

    companion object {
        const val ROW_LIST = "rowList"
        const val POSITION = "position"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_fragments)

        val rowList = intent.getParcelableArrayListExtra<FirstRow>(ROW_LIST)
        val position = intent.getIntExtra(POSITION, -1)

        val fragmentPhoto = PhotoFragment.newInstance(rowList[position].url)

        if(savedInstanceState == null){
            supportFragmentManager
                .beginTransaction()
                .add(R.id.activity_with_fragments, fragmentPhoto, "Photo")
                .commit()
        }
    }

}
