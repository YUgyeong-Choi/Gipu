package com.example.gipu_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FoodbankListAdapter(val FoodbankList: ArrayList<FoodBankData>): RecyclerView.Adapter<FoodbankListAdapter.FoodbankViewHolder>()  {
    inner class FoodbankViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val region = itemView.findViewById<TextView>(R.id.region_category)
        val name = itemView.findViewById<TextView>(R.id.center_name)
        val telephone = itemView.findViewById<TextView>(R.id.center_num)
        val location = itemView.findViewById<TextView>(R.id.center_location)
        val detail_location = itemView.findViewById<TextView>(R.id.center_detail_location)
        val centerDetailBtn = itemView.findViewById<Button>(R.id.center_detailInfo)
        val centerWishProductBtn = itemView.findViewById<Button>(R.id.center_wishproduct)

        init {
            centerDetailBtn.setOnClickListener {
                val pushData = FoodbankList[adapterPosition]
                val simpleDialog = FoodbankDetailActivity(pushData, itemView.context)
                simpleDialog.show()
            }
            centerWishProductBtn.setOnClickListener {
                val pushData = FoodbankList[adapterPosition]
                val simpleDialog = FoodbankPreferActivity(pushData, itemView.context)
                simpleDialog.show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodbankViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.foodbank_item, parent, false)
        return FoodbankViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FoodbankViewHolder, position: Int) {
        holder.region.text = regionData.data[FoodbankList[position].region]
        holder.name.text = centerNameData.data[FoodbankList[position].name]
        holder.telephone.text = FoodbankList[position].telephone
        holder.location.text = FoodbankList[position].location
        holder.detail_location.text = FoodbankList[position].locationDetail
    }

    override fun getItemCount(): Int {
        return FoodbankList.size
    }
}