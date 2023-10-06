package com.example.gipu_android

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LikeListAdapter(private var infoList: MutableList<InfoData>): RecyclerView.Adapter<LikeListAdapter.InfoViewHolder>() {

    inner class InfoViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val info_category = itemView.findViewById<TextView>(R.id.item_category)
        val info_title = itemView.findViewById<TextView>(R.id.item_title)
        val info_content = itemView.findViewById<TextView>(R.id.item_content)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.info_item, parent, false)
        return InfoViewHolder(itemView).apply{ //itemView 넘겨주기
            itemView.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val clickedItem = infoList[position]
                    (parent.context as Activity).finish()
                    val intent = Intent(parent.context, LikeDetailActivity::class.java)
                    intent.putExtra("info", clickedItem)
                    parent.context.startActivity(intent)
                }
            }
        }
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

    fun updateData(newLikeList: MutableList<InfoData>) {
        infoList = newLikeList
        notifyDataSetChanged()
    }
}