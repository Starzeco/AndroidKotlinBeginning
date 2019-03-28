package com.example.bmi


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import java.lang.Double
import kotlin.coroutines.coroutineContext

class CustomAdapter(val heightList: ArrayList<String>, val massList: ArrayList<String>, val bmiList: ArrayList<String>, val dateList:ArrayList<String>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.history_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = bmiList.size

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.bmi_row.text = bmiList[position]
        /*when(bmiList[position]){
            in 0.1..18.49 -> {
                //holder.bmi_row.setTextColor(Color.parseColor(R.color.grynszpan))
            }
            in 18.5..24.99 -> {
               // holder.bmi_row.setTextColor(resources.getColor(R.color.colorPrimary))
            }
            in 25.0..29.99 -> {
               // holder.bmi_row.setTextColor(resources.getColor(R.color.pompeianRose))
            }
            in 30.0..34.99 -> {
               // holder.bmi_row.setTextColor(resources.getColor(R.color.LapisLazuli))
            }
            in 35.0..Double.POSITIVE_INFINITY -> {
               // holder.bmi_row.setTextColor(resources.getColor(R.color.colorPrimaryDark))
            }
        }*/
        holder.height_row.text = heightList[position]
        holder.mass_row.text = massList[position]
        holder.date_row.text = dateList[position]
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val bmi_row: TextView = itemView.findViewById(R.id.bmi_row_id)
        val height_row: TextView = itemView.findViewById(R.id.height_row_id)
        val mass_row: TextView = itemView.findViewById(R.id.mass_display_row_id)
        val date_row: TextView = itemView.findViewById(R.id.date_row_id)
    }

}