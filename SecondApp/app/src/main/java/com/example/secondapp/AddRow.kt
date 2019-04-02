package com.example.secondapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.URLUtil
import com.example.secondapp.row_models.FirstRow
import kotlinx.android.synthetic.main.activity_add_row.*

class AddRow : AppCompatActivity() {

    companion object {
        const val CREATED_ROW_LIST = "createdRowList"
    }
    val rowList = ArrayList<FirstRow>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_row)

        add_button.setOnClickListener {
            if(validate()){

                val tags = tags_input.text.toString().splitToSequence(',').filter { it.isNotBlank() }.toList()
                val arrayTags = ArrayList<String>()
                arrayTags.addAll(tags)
                val name = name_input.text.toString()
                val url = url_input.text.toString()
                val date = date_input.text.toString()
                rowList.add(FirstRow(name, url, date, arrayTags))
            }else{
                clearInputs()
            }
        }
        imageButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putParcelableArrayListExtra(CREATED_ROW_LIST, rowList)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }

    }
    private fun validate(): Boolean {
        val name = name_input.text.toString()
        val url = url_input.text.toString()
        if(!URLUtil.isValidUrl(url)) url_input.error = getString(R.string.error_url)
        if(name.length !in 6..29) name_input.error = getString(R.string.error_name)
        return URLUtil.isValidUrl(url) && name.length <30 && name.length > 5
    }
    private fun clearInputs() {
        name_input.setText("")
        url_input.setText("")
        date_input.setText("")
        tags_input.setText("")
    }
}
