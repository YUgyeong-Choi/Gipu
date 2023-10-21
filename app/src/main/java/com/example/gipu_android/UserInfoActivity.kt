package com.example.gipu_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gipu_android.databinding.UserinfoActivityBinding

class UserInfoActivity:AppCompatActivity() {
    private val binding by lazy{
        UserinfoActivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}