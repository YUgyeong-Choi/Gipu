package com.example.gipu_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class InfoListAdapter(private val infoList: MutableList<InfoData>):RecyclerView.Adapter<InfoListAdapter.InfoViewHolder>() {

    inner class InfoViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val info_category = itemView.findViewById<TextView>(R.id.item_category)
        val info_title = itemView.findViewById<TextView>(R.id.item_title)
        val info_content = itemView.findViewById<TextView>(R.id.item_content)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.info_item, parent, false)
        return InfoViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.info_category.text = infoList[position].category
        if (infoList[position].category == "기부 주기") {
            holder.info_category.setBackgroundResource(R.drawable.background_give)
        }else{
            holder.info_category.setBackgroundResource(R.drawable.background_category)
        }
        holder.info_title.text = infoList[position].title
        holder.info_content.text = infoList[position].content
    }

    override fun getItemCount(): Int {
        return infoList.size
    }
}