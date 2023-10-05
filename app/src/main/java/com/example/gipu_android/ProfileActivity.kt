package com.example.gipu_android

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
    }
}