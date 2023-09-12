package com.example.gipu_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class InfoListActivity : AppCompatActivity(){

    val InfoList = arrayListOf(
        InfoData("카테고리입니다", "제목입니다", "본문입니다", "위치입니다"),
        InfoData("카테고리입니다", "제목입니다", "본문입니다", "위치입니다"),
        InfoData("카테고리입니다", "제목입니다", "본문입니다", "위치입니다"),
        InfoData("카테고리입니다", "제목입니다", "본문입니다", "위치입니다"),
        InfoData("카테고리입니다", "제목입니다", "본문입니다", "위치입니다")
    )
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.infolist_activity)

        val info_rv = findViewById<RecyclerView>(R.id.info_recyclerView)
        info_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        info_rv.setHasFixedSize(true)
        info_rv.adapter = InfoListAdapter(InfoList)

    }
}