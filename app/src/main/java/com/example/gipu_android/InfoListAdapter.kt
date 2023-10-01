package com.example.gipu_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//infoList를 InfoData의 리스트 형태로 받고, 리사이클러뷰 선언을 해줌
class InfoListAdapter(private val infoList: MutableList<InfoData>):RecyclerView.Adapter<InfoListAdapter.InfoViewHolder>() {
    //2. inner class로 info_item.xml 레이아웃의 카테고리, 제목, 본문 변수를 가지고 옴
    inner class InfoViewHolder(itemView : View):RecyclerView.ViewHolder(itemView){
        val info_category = itemView.findViewById<TextView>(R.id.item_category)
        val info_title = itemView.findViewById<TextView>(R.id.item_title)
        val info_content = itemView.findViewById<TextView>(R.id.item_content)
    }

    //1. 리사이클러뷰 안에 info_item레이아웃을 사용할거라고 선언해줌
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.info_item, parent, false)
        return InfoViewHolder(itemView)
    }

    //3. 리스트마다 가지고 있는 값들을 info_item의 변수에 대입시켜줌
    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.info_category.text = infoList[position].category
        holder.info_title.text = infoList[position].title
        holder.info_content.text = infoList[position].content
    }

    //리스트의 개수를 반환해줌
    override fun getItemCount(): Int {
        return infoList.size
    }
}