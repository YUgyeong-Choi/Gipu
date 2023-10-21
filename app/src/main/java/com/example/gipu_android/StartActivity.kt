package com.example.gipu_android

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.util.Log
import com.google.firebase.FirebaseApp
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class StartActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_activity)

        FirebaseApp.initializeApp(this)

        ProfileActivity.UserDB.init(this)
//        val InfoData = ProfileActivity.UserDB.getInstance().edit()
//        InfoData.clear()
//        InfoData.apply()

        if (ProfileActivity.UserDB.isLoggedIn()) {
            // 로그인 상태이면 Infolistactivity로 이동
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, InfoListActivity::class.java)
                startActivity(intent)
                finish() //이전 액티비티 종료
            }, 3000)
        } else {
            // 로그인 상태가 아니면 LoginActivity로 이동
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish() //이전 액티비티 종료
            }, 3000)
        }
        //getHashKey()
    }

    private fun getHashKey() {
        var packageInfo: PackageInfo? = null
        try {
            packageInfo = packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES)
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }
        if (packageInfo == null) Log.e("KeyHash", "KeyHash:null")
        for (signature in packageInfo!!.signatures) {
            try {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
    }

}