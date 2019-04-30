package com.example.secondapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.secondapp.R
import com.squareup.picasso.Picasso

class PhotoFragment: Fragment() {
    companion object {
        const val URL = "url"

        fun newInstance(url: String): PhotoFragment{
            val bundle = Bundle()
            bundle.putString(URL, url)
            val fragment = PhotoFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.photo_fragment, container, false)
        val image = view.findViewById<ImageView>(R.id.photo)
        Picasso.get().load(arguments?.getString(URL)).resize(70, 50).error(R.drawable.no_photo).into(image)
        return view
    }

}