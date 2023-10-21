package com.example.gipu_android

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.gipu_android.databinding.ProfileActivityBinding
import com.kakao.sdk.user.UserApiClient

class ProfileActivity: AppCompatActivity() {

    object UserDB{
        private lateinit var sharedPreferences: SharedPreferences

        fun init(context: Context) {
            sharedPreferences = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        }
        fun isLoggedIn(): Boolean {
            return sharedPreferences.contains("user") // 예를 들어 "username"이라는 키가 있다면 로그인 상태로 간주
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
            overridePendingTransition(0, 0);
            finish()
        }

        binding.profileSearch.setOnClickListener {
            val intent = Intent(this, FoodbankListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0);
            finish()
        }

        binding.profileChat.setOnClickListener {
            val intent = Intent(this, ChatListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0);
            finish()
        }

        binding.profileLikePost.setOnClickListener {
            val intent = Intent(this, LikeListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            finish()
        }

        binding.profileMyPost.setOnClickListener {
            val intent = Intent(this, MyListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            finish()
        }

        UserDB.init(this)
        val userName = UserDB.getInstance().getString("user", "")
        val userImage = UserDB.getInstance().getString("userImage", "")
        Glide.with(this).load(userImage).circleCrop().into(binding.profileUserImage)
        binding.profileNickname.text = userName

        binding.profileLogout.setOnClickListener {
            //연동 초기화
            kakaoUnlink()
            //유저 정보 삭제
            val InfoData = UserDB.getInstance().edit()
            InfoData.clear()
            InfoData.apply()
        }
    }
    private fun kakaoUnlink() {
        // 연결 끊기
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Log.e("Hello", "연결 끊기 실패", error)
            } else {
                Log.i("Hello", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
        finish()
    }
}