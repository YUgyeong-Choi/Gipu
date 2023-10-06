package com.example.gipu_android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gipu_android.databinding.ProfileActivityBinding

class ProfileActivity: AppCompatActivity() {
    private val binding by lazy{
        ProfileActivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.profileDocument.setOnClickListener {
            val intent = Intent(this, InfoListActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.profileSearch.setOnClickListener {
            val intent = Intent(this, FoodbankListActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.profileLikePost.setOnClickListener {
            val intent = Intent(this, LikeListActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}