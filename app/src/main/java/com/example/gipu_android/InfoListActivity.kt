package com.example.gipu_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gipu_android.databinding.InfolistActivityBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore




class InfoListActivity : AppCompatActivity(){

    //레이아웃의 infolist_activity를 사용하겠다
    private val binding by lazy{
        InfolistActivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root) //레이아웃 실행


        //글 작성 클릭하면 해당 화면으로
        binding.infoWriting.setOnClickListener{
            val intent = Intent(this, WritingActivity::class.java)
            startActivity(intent)
            finish()
        }

        //하단바 검색 클릭하면 푸드마켓 화면으로
        binding.infolistSearch.setOnClickListener {
            val intent = Intent(this, FoodbankListActivity::class.java)
            startActivity(intent)
            finish()
        }

        //InfoList는 InfoData형태가 여러개 인 리스트
        //InfoData는 LayoutData.kt에서 확인할 수 있음
        val InfoList = arrayListOf(
            InfoData("카테고리입니다", "제목입니다", "본문입니다"),
            InfoData("카테고리입니다", "제목입니다", "본문입니다"),
            InfoData("카테고리입니다", "제목입니다", "본문입니다"),
            InfoData("카테고리입니다", "제목입니다", "본문입니다"),
            InfoData("카테고리입니다", "제목입니다", "본문입니다")
        )

        //데이터 베이스에서 값 가지고 오는 코드 ^^ S2
        //주석처리된거 다 드래그앤 드랍하고 ctrl + / 하면 주석 풀림
        //Logcat을 보면 어떻게 출력되는 지 볼 수 있음
//        val db = FirebaseFirestore.getInstance()
//        db.collection("게시물")
//            .get()
//            .addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    for (document in task.result!!) {
//                        Log.d("문서", "${document.id} , ${document.data}")
//                        // 여기서 document.data를 사용하여 데이터를 처리할 수 있습니다.
//                    }
//                } else {
//                    Log.w("오류", "Error getting documents.", task.exception)
//                }
//            }

        //레이아웃의 리사이클러뷰 가지고 옴
        val info_rv = findViewById<RecyclerView>(R.id.info_recyclerView)
        //리사이클러뷰는 수직으로 내려가는 형태
        info_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        //이건뭔지 모름 리사이클러뷰 사이즈 조정하는 것 같은데
        info_rv.setHasFixedSize(true)
        //리사이클러뷰를 사용할 땐 리사이클러뷰 안에 들어가는 Layout-info_item을 설정해줘야하기 때문에 어댑터가 필요함
        // -> InfoListAdapter를 실행시키는데 InfoList를 넘겨줌
        info_rv.adapter = InfoListAdapter(InfoList)

    }
}