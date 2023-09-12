package com.example.gipu_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InfoListAdapter(val infoList: ArrayList<InfoData>):RecyclerView.Adapter<InfoListAdapter.InfoViewHolder>() {
    inner class InfoViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val info_category = itemView.findViewById<TextView>(R.id.item_category)
        val info_title = itemView.findViewById<TextView>(R.id.item_title)
        val info_content = itemView.findViewById<TextView>(R.id.item_content)
        val info_location = itemView.findViewById<TextView>(R.id.item_location)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.info_item, parent, false)
        return InfoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.info_category.text = infoList[position].category
        holder.info_title.text = infoList[position].title
        holder.info_content.text = infoList[position].content
        holder.info_location.text = infoList[position].location
    }

    override fun getItemCount(): Int {
        return infoList.size
    }
}