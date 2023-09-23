package com.example.gipu_android

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper

class StartActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, InfoListActivity::class.java)
            startActivity(intent)
            finish() //이전 액티비티 종료
        }, 3000)
    }
}