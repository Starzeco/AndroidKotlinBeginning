package com.example.bmi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(val heightList: ArrayList<String>, val massList: ArrayList<String>, val bmiList: ArrayList<String>, val dateList:ArrayList<String>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.history_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = bmiList.size

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.bmi_row.text = bmiList[position]
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