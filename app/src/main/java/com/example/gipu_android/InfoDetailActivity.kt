package com.example.gipu_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gipu_android.databinding.InfodetailActivityBinding

class InfoDetailActivity: AppCompatActivity() {
    private val binding by lazy{
        InfodetailActivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val detailInfo = intent.getSerializableExtra("info") as InfoData?


        binding.infodetailCategory.text = detailInfo?.category
        binding.infodetailTitle.text = detailInfo?.title
        binding.infodetailContent.text = detailInfo?.content
    }
}