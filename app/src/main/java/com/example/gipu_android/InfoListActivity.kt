package com.example.gipu_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gipu_android.databinding.InfolistActivityBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore




class InfoListActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.infolist_activity) //레이아웃 실행


        //글 작성 클릭하면 해당 화면으로
        val writingBtn: ImageView = findViewById(R.id.info_writing)
        writingBtn.setOnClickListener{
            val intent = Intent(this, WritingActivity::class.java)
            startActivity(intent)
            finish()
        }

        //하단바 검색 클릭하면 이동
        val foodmarketBtn: ImageView = findViewById(R.id.infolist_search)
        foodmarketBtn.setOnClickListener {
            val intent = Intent(this, FoodbankListActivity::class.java)
            startActivity(intent)
            finish()
        }

        //하단바 프로필 클릭하면 이동
        val profileBtn : ImageView = findViewById(R.id.infolist_profile)
        profileBtn.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

        val db = FirebaseFirestore.getInstance()
        db.collection("게시물")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val InfoList = mutableListOf<InfoData>()

                    for (document in task.result!!) {
                        val documentData = document.data
                        val category = documentData["category"] as String
                        val title = documentData["title"] as String // 실제 필드 이름으로 수정
                        val content = documentData["content"] as String // 실제 필드 이름으로 수정
                        val infoData = InfoData(category, title, content)

                        InfoList.add(infoData)
                        //Log.d("문서", "${document.id} , ${document.data}")
                        // 여기서 document.data를 사용하여 데이터를 처리할 수 있습니다.
                    }
                    Log.d("출력", InfoList.toString())
                    //레이아웃의 리사이클러뷰 가지고 옴
                    val info_rv = findViewById<RecyclerView>(R.id.info_recyclerView)
                    //리사이클러뷰는 수직으로 내려가는 형태
                    info_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                    //이건뭔지 모름 리사이클러뷰 사이즈 조정하는 것 같은데
                    info_rv.setHasFixedSize(true)
                    //리사이클러뷰를 사용할 땐 리사이클러뷰 안에 들어가는 Layout-info_item을 설정해줘야하기 때문에 어댑터가 필요함
                    // -> InfoListAdapter를 실행시키는데 InfoList를 넘겨줌
                    info_rv.adapter = InfoListAdapter(InfoList)
                } else {
                    Log.w("오류", "Error getting documents.", task.exception)
                }
            }



    }
}