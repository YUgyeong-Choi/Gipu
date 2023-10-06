package com.example.gipu_android

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gipu_android.databinding.InfodetailActivityBinding
import com.google.gson.Gson

class LikeDetailActivity: AppCompatActivity() {

    private val binding by lazy{
        InfodetailActivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.infodetailBack.setOnClickListener {
            val intent = Intent(this, LikeListActivity::class.java)
            startActivity(intent)
            finish()
        }

        val detailInfo = intent.getSerializableExtra("info") as InfoData

        // 찜 상태에 따라 이미지 변환
        InfoDetailActivity.HeartDB.init(this)
        val InfoData = InfoDetailActivity.HeartDB.getInstance()
        if (InfoData.contains(detailInfo.writer)){
            binding.infodetailStar.setImageResource(R.drawable.fullstar)
        }else{
            binding.infodetailStar.setImageResource(R.drawable.emptystar)
        }

        binding.infodetailStar.setOnClickListener{
            InfoDetailActivity.HeartDB.init(this)
            val InfoData = InfoDetailActivity.HeartDB.getInstance()

            if(InfoData.contains(detailInfo.writer)){
                val editor = InfoData.edit()
                editor.remove(detailInfo.writer)
                editor.apply()
                binding.infodetailStar.setImageResource(R.drawable.emptystar)
            }else{
                val editor = InfoData.edit()
                val key = detailInfo.writer
                val gson = Gson()
                val InfoJson = gson.toJson(detailInfo)
                editor.putString(key, InfoJson)
                editor.apply()
                binding.infodetailStar.setImageResource(R.drawable.fullstar)
            }
        }

        binding.infodetailCategory.text = detailInfo.category
        binding.infodetailTitle.text = detailInfo.title
        binding.infodetailContent.text = detailInfo.content
    }
}