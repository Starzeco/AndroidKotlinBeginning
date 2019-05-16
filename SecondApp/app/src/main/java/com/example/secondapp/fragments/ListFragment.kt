package com.example.secondapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.secondapp.R
import com.example.secondapp.row_models.FirstRow
import com.squareup.picasso.Picasso

class ListFragment: Fragment() {

    companion object {
        const val ROW_LIST = "rowList"
        const val POSITION = "position"

        fun newInstance(rowList: ArrayList<FirstRow>, position: Int): ListFragment{
            val bundle = Bundle()
            bundle.putParcelableArrayList(ROW_LIST, rowList)
            bundle.putInt(POSITION, position)
            val fragment = ListFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        val firstImage = view.findViewById<ImageView>(R.id.first_photo)
        val secondImage = view.findViewById<ImageView>(R.id.second_photo)
        val thirdImage = view.findViewById<ImageView>(R.id.third_photo)
        val fourthImage = view.findViewById<ImageView>(R.id.fourth_photo)
        val fifthImage = view.findViewById<ImageView>(R.id.fifth_photo)
        val sixthImage = view.findViewById<ImageView>(R.id.sixth_photo)
        val listOfRows = findSixSimilar()
        val listOfImages:ArrayList<ImageView> = arrayListOf(firstImage, secondImage, thirdImage, fourthImage, fifthImage, sixthImage)
        for (i in 0 until listOfRows.size){
            Picasso.get().load(listOfRows[i].url).resize(70, 50).error(R.drawable.no_photo).into(listOfImages[i])
        }
        return view
    }

    private fun findSixSimilar(): ArrayList<FirstRow>{
        val rowList = arguments!!.getParcelableArrayList<FirstRow>(ROW_LIST)
        val position: Int = arguments!!.getInt(POSITION)
        val row = rowList.removeAt(position)
        rowList!!.sortWith(compareBy { it.countFitness(row) })
        return ArrayList(rowList.takeLast(6).filter { it.countFitness(row) != 0 })

    }
}