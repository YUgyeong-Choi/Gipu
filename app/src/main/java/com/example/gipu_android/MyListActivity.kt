package com.example.gipu_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gipu_android.databinding.LikelistActivityBinding
import com.google.firebase.firestore.FirebaseFirestore

class MyListActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mylist_activity)

        val backBtn: ImageView = findViewById(R.id.mylist_back)
        backBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
            finish()
        }

        ProfileActivity.UserDB.init(this)
        val user = ProfileActivity.UserDB.getInstance().getString("user", "")

        val db = FirebaseFirestore.getInstance()
        db.collection("게시물")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val InfoList = mutableListOf<InfoData>()

                    for (document in task.result!!) {
                        val documentData = document.data

                        if(documentData["writer"] == user){
                            val category = documentData["category"] as String
                            val title = documentData["title"] as String// 실제 필드 이름으로 수정
                            val content = documentData["content"] as String// 실제 필드 이름으로 수정
                            val si = documentData["si"] as String
                            val dong = documentData["dong"] as String
                            val writer = documentData["writer"] as String
                            val infoData = InfoData(category, title, content, si, dong, writer)

                            InfoList.add(infoData)
                            //Log.d("문서", "${document.id} , ${document.data}")
                        }
                    }
                    Log.d("출력", InfoList.toString())
                    val info_rv = findViewById<RecyclerView>(R.id.my_RecyclerView)
                    info_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    info_rv.setHasFixedSize(true)
                    info_rv.adapter = MyListAdapter(InfoList)
                } else {
                    Log.w("오류", "Error getting documents.", task.exception)
                }
            }
    }
}