package com.example.gipu_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gipu_android.databinding.LikelistActivityBinding
import com.google.gson.Gson

class LikeListActivity: AppCompatActivity() {
    private lateinit var heartAdapter : LikeListAdapter

    private val binding by lazy{
        LikelistActivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.likelistBack.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        InfoDetailActivity.HeartDB.init(this)
        val InfoDatas= InfoDetailActivity.HeartDB.getInstance()
        val allEntries: Map<String, *> = InfoDatas.all
        val likeList = arrayListOf<InfoData>()

        if(allEntries.isEmpty()){
            Log.d("하트 내부저장소", "없음")
        }else{
            //Log.d("하트 내부저장소", allEntries.toString())
            for ((key, value) in allEntries){
                if (value is String) {
                    val gson = Gson()
                    val Info = gson.fromJson(value, InfoData::class.java)
                    likeList.add(Info)
                }
            }
        }

        val info_rv = findViewById<RecyclerView>(R.id.like_RecyclerView)
        info_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        info_rv.setHasFixedSize(true)
        heartAdapter = LikeListAdapter(likeList)
        info_rv.adapter = heartAdapter
    }

    override fun onResume() {
        super.onResume()

        InfoDetailActivity.HeartDB.init(this)
        val InfoDatas= InfoDetailActivity.HeartDB.getInstance() // 이 부분을 수정해야 합니다.
        val allEntries: Map<String, *> = InfoDatas.all
        val likeList = arrayListOf<InfoData>()

        if(allEntries.isEmpty()){
            Log.d("하트 내부저장소", "없음")
        }else{
            //Log.d("하트 내부저장소", allEntries.toString())
            for ((key, value) in allEntries){
                if (value is String) {
                    val gson = Gson()
                    val Info = gson.fromJson(value, InfoData::class.java)
                    likeList.add(Info)
                }
            }
        }

        heartAdapter.updateData(likeList)
    }
}