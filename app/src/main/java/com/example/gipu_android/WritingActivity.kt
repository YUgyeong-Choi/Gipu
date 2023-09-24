package com.example.gipu_android

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextWatcher
import android.text.Editable
import android.util.Base64
import android.util.Log
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gipu_android.databinding.InfolistActivityBinding
import com.example.gipu_android.databinding.WritingActivityBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

class WritingActivity: AppCompatActivity() {
    private val binding by lazy{
        WritingActivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lateinit var title: String
        lateinit var content: String


        binding.writingBack.setOnClickListener {
            val intent = Intent(this, InfoListActivity::class.java)
            startActivity(intent)
            finish()
        }

        with(binding) {
            writingTitle.addTextChangedListener(object : TextWatcher {
                var maxText = ""
                override fun beforeTextChanged(pos: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    maxText = pos.toString()
                }

                override fun onTextChanged(pos: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val toast = Toast.makeText(
                        this@WritingActivity,
                        "최대 18자까지 입력 가능합니다.",
                        Toast.LENGTH_SHORT
                    )
                    if (writingTitle.length() > 18) {
                        writingTitle.setText(maxText)
                        writingTitle.setSelection(writingTitle.length())
                        toast.show()
                    } else {
                        toast.cancel()
                    }

                    title = pos.toString()
                }

                override fun afterTextChanged(p0: Editable?) {
                    // 텍스트 변경 후에 수행할 작업
                }
            })

            writingContent.addTextChangedListener(object : TextWatcher {
                var maxText = ""
                override fun beforeTextChanged(pos: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    maxText = pos.toString()
                }

                override fun onTextChanged(pos: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val toast = Toast.makeText(
                        this@WritingActivity,
                        "최대 1000자까지 입력 가능합니다.",
                        Toast.LENGTH_SHORT
                    )
                    if (writingContent.length() > 1000) {
                        writingContent.setText(maxText)
                        writingContent.setSelection(writingTitle.length())
                        toast.show()
                    } else {
                        toast.cancel()
                    }
                    content = pos.toString()
                }

                override fun afterTextChanged(p0: Editable?) {
                    // 텍스트 변경 후에 수행할 작업
                }
            })

        }

        binding.writingFinish.setOnClickListener {
            val db = Firebase.firestore
            val testData = hashMapOf(
                "title" to title,
                "content" to content
            )
            val keyHash = getHashKey()
            db.collection("게시물").document(keyHash.toString()).set(testData)

            val intent = Intent(this, InfoListActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.camera.setOnClickListener {
            var popupMenu = PopupMenu(applicationContext, it)

            menuInflater.inflate(R.menu.camera_menu, popupMenu.menu)
            popupMenu.show()
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.test1 -> {
                        Toast.makeText(applicationContext, "사진보관함", Toast.LENGTH_SHORT).show()
                        return@setOnMenuItemClickListener true
                    }
                    R.id.test2 -> {
                        Toast.makeText(applicationContext, "촬영", Toast.LENGTH_SHORT).show()
                        return@setOnMenuItemClickListener true
                    }else ->{
                        return@setOnMenuItemClickListener false
                    }
                }
            }
        }
    }

    private fun getHashKey():String? {
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
                return Base64.encodeToString(md.digest(), Base64.DEFAULT)
            } catch (e: NoSuchAlgorithmException) {
                Log.e("KeyHash", "Unable to get MessageDigest. signature=$signature", e)
            }
        }
        return null
    }
}