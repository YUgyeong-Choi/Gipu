package com.example.gipu_android

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MyListAdapter(private var infoList: MutableList<InfoData>): RecyclerView.Adapter<MyListAdapter.InfoViewHolder>() {

    inner class InfoViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        val info_category = itemView.findViewById<TextView>(R.id.item_category)
        val info_title = itemView.findViewById<TextView>(R.id.item_title)
        val info_content = itemView.findViewById<TextView>(R.id.item_content)
        val info_si = itemView.findViewById<TextView>(R.id.item_si)
        val info_dong = itemView.findViewById<TextView>(R.id.item_dong)
        val trash = itemView.findViewById<ImageView>(R.id.item_tag)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.info_item, parent, false)
        return InfoViewHolder(itemView).apply{ //itemView 넘겨주기
            itemView.setOnClickListener{
                val position = adapterPosition
                if(position != RecyclerView.NO_POSITION){
                    val clickedItem = infoList[position]
                    val activityContext = parent.context as? Activity
                    (parent.context as Activity).finish()
                    val intent = Intent(parent.context, MyDetailActivity::class.java)
                    intent.putExtra("info", clickedItem)
                    parent.context.startActivity(intent)
                    activityContext?.overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
                }
            }
        }
    }


    override fun onBindViewHolder(holder: InfoViewHolder, position: Int) {
        holder.info_category.text = infoList[position].category
        if (infoList[position].category == "기부 하기") {
            holder.info_category.setBackgroundResource(R.drawable.background_give)
        }else{
            holder.info_category.setBackgroundResource(R.drawable.background_category)
        }
        holder.info_title.text = infoList[position].title

        if (infoList[position].content.length <= 25) {
            holder.info_content.text = infoList[position].content
        } else {
            holder.info_content.text = infoList[position].content.take(25) + "..."
        }

        holder.info_si.text = infoList[position].si
        holder.info_dong.text = infoList[position].dong
        holder.trash.visibility = View.VISIBLE
        holder.trash.setImageResource(R.drawable.trash)

        holder.trash.setOnClickListener {
            val simpleDialog = MyInfoRemoveActivity(holder.itemView.context)
            simpleDialog.show( object : MyInfoRemoveActivity.OnDialogResultListener {
                override fun onResult(result: Boolean) {
                    if (result == true) {

                        val DBname = infoList[holder.adapterPosition].writer + "_" + infoList[holder.adapterPosition].title
                        val db = Firebase.firestore
                        val collectionRef = db.collection("게시물")

                        // 해당 문서 삭제
                        collectionRef.document(DBname)
                            .delete()
                            .addOnSuccessListener {
                                // 삭제 성공 시 처리
                                Log.d("Firestore", "Document successfully deleted!")
                            }
                            .addOnFailureListener { exception ->
                                // 삭제 실패 시 처리
                                Log.w("Firestore", "Error deleting document: ", exception)
                            }

                        InfoDetailActivity.HeartDB.init(holder.itemView.context)
                        val InfoData = InfoDetailActivity.HeartDB.getInstance()

                        if (InfoData.contains(DBname)) {
                            val editor = InfoData.edit()
                            editor.remove(DBname)
                            editor.apply()
                        }

                        // infoList에서 해당 항목을 찾아 제거
                        val removedItem = infoList.find { it.writer + "_" + it.title == DBname }
                        if (removedItem != null) {
                            val position = infoList.indexOf(removedItem)
                            infoList.removeAt(holder.adapterPosition)
                            notifyItemRemoved(holder.adapterPosition)
                        }
                    }
                }
            })
        }
    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    fun updateData(newLikeList: MutableList<InfoData>) {
        infoList = newLikeList
        notifyDataSetChanged()
    }
}