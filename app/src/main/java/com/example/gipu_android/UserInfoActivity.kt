package com.example.gipu_android

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.gipu_android.databinding.UserinfoActivityBinding

class UserInfoActivity:AppCompatActivity() {
    private val binding by lazy{
        UserinfoActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val imageUrl = intent.getStringExtra("IMAGE_URL")

        Glide.with(this).load(imageUrl).circleCrop().into(binding.userinfoProfileImage)

        binding.userinfoNext.setOnClickListener {
            if (binding.userinfoNickname.text.isBlank()) {
                Toast.makeText(this, "닉네임을 작성해주세요", Toast.LENGTH_SHORT).show()
            }else if(binding.userinfoSi.text.isBlank() || binding.userinfoDong.text.isBlank()){
                Toast.makeText(applicationContext, "지역을 작성해주세요", Toast.LENGTH_SHORT).show()
            }else{
                ProfileActivity.UserDB.init(this)
                val InfoData = ProfileActivity.UserDB.getInstance().edit()

                InfoData.putString("userImage", imageUrl)
                InfoData.putString("user", binding.userinfoNickname.text.toString())
                InfoData.putString("si", binding.userinfoSi.text.toString())
                InfoData.putString("dong", binding.userinfoDong.text.toString())
                InfoData.apply()

                val intent = Intent(this, StartActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit);
                finish()
            }
        }

    }
}