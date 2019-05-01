package com.example.secondapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.secondapp.R
import com.example.secondapp.row_models.FirstRow

class DetailFragment: Fragment() {
    companion object {
        const val ROW = "row"

        fun newInstance(row: FirstRow): DetailFragment{
            val bundle = Bundle()
            bundle.putParcelable(ROW, row)
            val fragment = DetailFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.detail_fragment, container, false)
        val name = view.findViewById<TextView>(R.id.name_input_details)
        val tags = view.findViewById<TextView>(R.id.tags_input_details)
        val row = arguments!!.getParcelable<FirstRow>(ROW)
        name.text = row?.name
        tags.text = row!!.tags.joinToString(", ")
        return view
    }
}