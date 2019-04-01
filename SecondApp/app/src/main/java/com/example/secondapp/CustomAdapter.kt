package com.example.secondapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class CustomAdapter(val imageList:ArrayList<String>, val nameList: ArrayList<String>, val dateList: ArrayList<String>, val tagList:ArrayList<String>): RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val image: ImageView = itemView.findViewById(R.id.picasso_image)
        val date: TextView = itemView.findViewById(R.id.date)
        val tags: TextView = itemView.findViewById(R.id.tags)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomAdapter.ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.one_row, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = nameList.size

    override fun onBindViewHolder(holder: CustomAdapter.ViewHolder, position: Int) {
        holder.name.text = nameList[position]
        Picasso.get().load(imageList[position]).error(R.drawable.no_photo).into(holder.image)
        holder.date.text = dateList[position]
        holder.tags.text = tagList[position]
    }

    fun removeItem(viewHolder: RecyclerView.ViewHolder) {
        imageList.removeAt(viewHolder.adapterPosition)
        nameList.removeAt(viewHolder.adapterPosition)
        dateList.removeAt(viewHolder.adapterPosition)
        tagList.removeAt(viewHolder.adapterPosition)
        notifyItemRemoved(viewHolder.adapterPosition)
    }


}