package com.example.gipu_android

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.gipu_android.databinding.InfodetailActivityBinding
import com.google.gson.Gson

class InfoDetailActivity: AppCompatActivity() {
    object HeartDB{
        private lateinit var sharedPreferences: SharedPreferences

        fun init(context: Context) {
            sharedPreferences = context.getSharedPreferences("HeartInfo", Context.MODE_PRIVATE)
        }

        fun getInstance(): SharedPreferences {
            if (!this::sharedPreferences.isInitialized) {
                throw IllegalStateException("SharedPreferencesSingleton is not initialized")
            }
            return sharedPreferences
        }
    }

    private val binding by lazy{
        InfodetailActivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.infodetailBack.setOnClickListener {
            finish()
        }

        val detailInfo = intent.getSerializableExtra("info") as InfoData
        val heartDB_name = detailInfo.writer + "_"+  detailInfo.title

        // 찜 상태에 따라 이미지 변환
        HeartDB.init(this)
        val InfoData = HeartDB.getInstance()

        if (InfoData.contains(heartDB_name)){
            binding.infodetailStar.setImageResource(R.drawable.fullstar)
        }else{
            binding.infodetailStar.setImageResource(R.drawable.emptystar)
        }

        binding.infodetailStar.setOnClickListener{
            HeartDB.init(this)
            val InfoDatas = HeartDB.getInstance()

            if(InfoDatas.contains(heartDB_name)){
                val editor = InfoDatas.edit()
                editor.remove(heartDB_name)
                editor.apply()
                binding.infodetailStar.setImageResource(R.drawable.emptystar)
            }else{
                val editor = InfoDatas.edit()
                val key = heartDB_name
                val gson = Gson()
                val InfoJson = gson.toJson(detailInfo)
                editor.putString(key, InfoJson)
                editor.apply()
                binding.infodetailStar.setImageResource(R.drawable.fullstar)
            }
        }

        binding.infodetailChatbtn.setOnClickListener {
            val intent = Intent(this, ChatRoomActivity::class.java)
            intent.putExtra("destinationUid", detailInfo.writer)
            intent.putExtra("roomName", detailInfo.title)
            startActivity(intent)
            finish()
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