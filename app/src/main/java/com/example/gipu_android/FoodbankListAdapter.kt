package com.example.gipu_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodbankListAdapter(val FoodbankList: ArrayList<FoodBankData>): RecyclerView.Adapter<FoodbankListAdapter.FoodbankViewHolder>()  {
    inner class FoodbankViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val region = itemView.findViewById<TextView>(R.id.region_category)
        val name = itemView.findViewById<TextView>(R.id.center_name)
        val telephone = itemView.findViewById<TextView>(R.id.center_num)
        val location = itemView.findViewById<TextView>(R.id.center_location)
        val detail_location = itemView.findViewById<TextView>(R.id.center_detail_location)
        val what = itemView.findViewById<TextView>(R.id.center_what)
        val who = itemView.findViewById<TextView>(R.id.center_who)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodbankViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.foodbank_item, parent, false)
        return FoodbankViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodbankViewHolder, position: Int) {
        holder.region.text = FoodbankList[position].region
        holder.name.text = FoodbankList[position].name
        holder.telephone.text = FoodbankList[position].telephone
        holder.location.text = FoodbankList[position].location
        holder.detail_location.text = FoodbankList[position].locationDetail
        holder.what.text = FoodbankList[position].what
        holder.who.text = FoodbankList[position].who
    }

    override fun getItemCount(): Int {
        return FoodbankList.size
    }
}