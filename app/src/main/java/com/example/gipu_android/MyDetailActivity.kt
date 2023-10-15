package com.example.gipu_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.gipu_android.databinding.InfodetailActivityBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

class MyDetailActivity: AppCompatActivity() {

    private val binding by lazy{
        InfodetailActivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.infodetailStar.setImageResource(R.drawable.trash)
        binding.infodetailChatbtn.visibility= View.GONE

        binding.infodetailBack.setOnClickListener {
            val intent = Intent(this, MyListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
            finish()
        }

        val detailInfo = intent.getSerializableExtra("info") as InfoData

        binding.infodetailStar.setOnClickListener{
            val simpleDialog = MyInfoRemoveActivity(this@MyDetailActivity)
            simpleDialog.show( object : MyInfoRemoveActivity.OnDialogResultListener {
                override fun onResult(result: Boolean) {
                    if (result == true) {

                        val DBname = detailInfo.writer + "_" + detailInfo.title
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

                        InfoDetailActivity.HeartDB.init(this@MyDetailActivity)
                        val InfoData = InfoDetailActivity.HeartDB.getInstance()

                        if (InfoData.contains(DBname)) {
                            val editor = InfoData.edit()
                            editor.remove(DBname)
                            editor.apply()
                        }

                        val intent = Intent(this@MyDetailActivity, MyListActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit)
                        finish()
                    }
                }
            })
        }

        if (detailInfo.imageUrl != null){
            binding.infodetailPostimage.isVisible = true
            Glide.with(this)
                .load(detailInfo.imageUrl)
                .into(binding.infodetailPostimage)
        }

        binding.infodetailCategory.text = detailInfo.category
        binding.infodetailTitle.text = detailInfo.title
        binding.infodetailContent.text = detailInfo.content
        binding.infodetailLocation.text = detailInfo.si +" " + detailInfo.dong
        binding.infodetailWriter.text = detailInfo.writer
    }
}