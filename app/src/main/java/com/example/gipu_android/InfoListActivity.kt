package com.example.gipu_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore




class InfoListActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.infolist_activity) //레이아웃 실행


        //글 작성 클릭하면 해당 화면으로
        val writingBtn: ImageView = findViewById(R.id.info_writing)
        writingBtn.setOnClickListener{
            val intent = Intent(this, InfoWritingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit);
            finish()
        }

        //하단바 검색 클릭하면 이동
        val foodmarketBtn: ImageView = findViewById(R.id.infolist_search)
        foodmarketBtn.setOnClickListener {
            val intent = Intent(this, FoodbankListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0);
            finish()
        }

        val chatBtn: ImageView = findViewById(R.id.infolist_chat)
        chatBtn.setOnClickListener {
            val intent = Intent(this, ChatListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0);
            finish()
        }

        //하단바 프로필 클릭하면 이동
        val profileBtn : ImageView = findViewById(R.id.infolist_profile)
        profileBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0);
            finish()
        }

        ProfileActivity.UserDB.init(this)
        val si = ProfileActivity.UserDB.getInstance().getString("si", "")
        val dong = ProfileActivity.UserDB.getInstance().getString("dong", "")
        //  시 게시물
        getSiDatas(si)

        val settingBtn : ImageView = findViewById(R.id.info_setting)
        settingBtn.setOnClickListener {
            var popupMenu = PopupMenu(applicationContext, it)
            menuInflater.inflate(R.menu.setting_menu, popupMenu.menu)

            popupMenu.menu.findItem(R.id.mysi).title = si + " 게시물"
            popupMenu.menu.findItem(R.id.mydong).title = dong + " 게시물"
            popupMenu.menu.findItem(R.id.all).title = "경남 게시물"

            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.mysi -> {
                        getSiDatas(si)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.mydong -> {
                        getDongDatas(si, dong)
                        return@setOnMenuItemClickListener true
                    }
                    R.id.all -> {
                        getAllDatas()
                        return@setOnMenuItemClickListener true
                    }else ->{
                    return@setOnMenuItemClickListener false
                }
                }
            }
        }
    }

    fun getAllDatas(){
        val db = FirebaseFirestore.getInstance()
        db.collection("게시물")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val InfoList = mutableListOf<InfoData>()

                    for (document in task.result!!) {
                        val documentData = document.data
                        val category = documentData["category"] as String
                        val title = documentData["title"] as String// 실제 필드 이름으로 수정
                        val content = documentData["content"] as String// 실제 필드 이름으로 수정
                        val si = documentData["si"] as String
                        val dong = documentData["dong"] as String
                        val writer = documentData["writer"] as String
                        val imageUrl = documentData["imageUrl"] as String?
                        val infoData = InfoData(category, title, content, si, dong, writer, imageUrl)

                        InfoList.add(infoData)
                        //Log.d("문서", "${document.id} , ${document.data}")
                    }
                    Log.d("출력", InfoList.toString())
                    val info_rv = findViewById<RecyclerView>(R.id.info_recyclerView)
                    info_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    info_rv.setHasFixedSize(true)
                    info_rv.adapter = InfoListAdapter(InfoList)
                } else {
                    Log.w("오류", "Error getting documents.", task.exception)
                }
            }
    }

    fun getSiDatas(si : String?){
        val db = FirebaseFirestore.getInstance()
        db.collection("게시물")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val InfoList = mutableListOf<InfoData>()

                    for (document in task.result!!) {
                        val documentData = document.data

                        if(documentData["si"] == si){
                            val category = documentData["category"] as String
                            val title = documentData["title"] as String// 실제 필드 이름으로 수정
                            val content = documentData["content"] as String// 실제 필드 이름으로 수정
                            val si = documentData["si"] as String
                            val dong = documentData["dong"] as String
                            val writer = documentData["writer"] as String
                            val imageUrl = documentData["imageUrl"] as String?
                            val infoData = InfoData(category, title, content, si, dong, writer, imageUrl)

                            InfoList.add(infoData)
                            //Log.d("문서", "${document.id} , ${document.data}")
                        }
                    }
                    Log.d("출력", InfoList.toString())
                    val info_rv = findViewById<RecyclerView>(R.id.info_recyclerView)
                    info_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    info_rv.setHasFixedSize(true)
                    info_rv.adapter = InfoListAdapter(InfoList)
                } else {
                    Log.w("오류", "Error getting documents.", task.exception)
                }
            }
    }

    fun getDongDatas(si: String?, dong: String?){
        val db = FirebaseFirestore.getInstance()
        db.collection("게시물")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val InfoList = mutableListOf<InfoData>()

                    for (document in task.result!!) {
                        val documentData = document.data

                        if(documentData["si"] == si && documentData["dong"] == dong){
                            val category = documentData["category"] as String
                            val title = documentData["title"] as String// 실제 필드 이름으로 수정
                            val content = documentData["content"] as String// 실제 필드 이름으로 수정
                            val si = documentData["si"] as String
                            val dong = documentData["dong"] as String
                            val writer = documentData["writer"] as String
                            val imageUrl = documentData["imageUrl"] as? String?
                            val infoData = InfoData(category, title, content, si, dong, writer, imageUrl)

                            InfoList.add(infoData)
                            //Log.d("문서", "${document.id} , ${document.data}")
                        }
                    }
                    Log.d("출력", InfoList.toString())
                    val info_rv = findViewById<RecyclerView>(R.id.info_recyclerView)
                    info_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    info_rv.setHasFixedSize(true)
                    info_rv.adapter = InfoListAdapter(InfoList)
                } else {
                    Log.w("오류", "Error getting documents.", task.exception)
                }
            }
    }

}