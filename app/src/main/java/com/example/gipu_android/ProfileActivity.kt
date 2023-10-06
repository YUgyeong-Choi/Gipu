package com.example.gipu_android

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gipu_android.databinding.ProfileActivityBinding

class ProfileActivity: AppCompatActivity() {

    object UserDB{
        private lateinit var sharedPreferences: SharedPreferences

        fun init(context: Context) {
            sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        }

        fun getInstance(): SharedPreferences {
            if (!this::sharedPreferences.isInitialized) {
                throw IllegalStateException("SharedPreferencesSingleton is not initialized")
            }
            return sharedPreferences
        }
    }

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

        binding.profileMyPost.setOnClickListener {
            val intent = Intent(this, MyListActivity::class.java)
            startActivity(intent)
            finish()
        }


//        UserDB.init(this)
//        val InfoData = UserDB.getInstance().edit()
////        InfoData.clear()
////        InfoData.apply()
////
//        InfoData.putString("user", "바니바니")
//        InfoData.putString("si", "진주시")
//        InfoData.putString("dong", "초전동")
//        InfoData.apply()

        UserDB.init(this)
        val userName = UserDB.getInstance().getString("user", "")
        binding.profileNickname.text = userName
    }
}