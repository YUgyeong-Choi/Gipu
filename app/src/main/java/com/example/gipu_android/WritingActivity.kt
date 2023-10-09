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
import androidx.core.app.ActivityCompat
import com.example.gipu_android.databinding.InfolistActivityBinding
import com.example.gipu_android.databinding.WritingActivityBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import android.Manifest

class WritingActivity: AppCompatActivity() {
    private val binding by lazy{
        WritingActivityBinding.inflate(layoutInflater)
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 권한이 허용됨
                    // 권한이 허용되었을 때 실행할 작업을 여기에 추가합니다.
                } else {
                    // 권한이 거부됨
                    // 권한이 거부되었을 때 실행할 작업을 여기에 추가합니다.
                }
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        lateinit var title: String
        lateinit var content: String


        binding.writingBack.setOnClickListener {
            val intent = Intent(this, InfoListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_left_enter, R.anim.slide_left_exit);
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



                binding.camera.setOnClickListener {
                    // 권한 요청
                    ActivityCompat.requestPermissions(
                        this@WritingActivity,
                        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                        1
                    )
                }


        var category = "null"

        binding.writingGive.setOnClickListener {
            binding.writingGive.setBackgroundResource(R.drawable.background_category)
            binding.writingGet.setBackgroundResource(R.drawable.background_gray)
            category = "기부 하기"
        }

        binding.writingGet.setOnClickListener {
            binding.writingGet.setBackgroundResource(R.drawable.background_category)
            binding.writingGive.setBackgroundResource(R.drawable.background_gray)
            category = "기부 받기"
        }

        binding.writingFinish.setOnClickListener {
            if(category == "null"){
                val toast = Toast.makeText(
                    this@WritingActivity,
                    "기부 종류를 설정해주세요",
                    Toast.LENGTH_SHORT
                )
                toast.show()
            }else{
                val si = ProfileActivity.UserDB.getInstance().getString("si","")
                val dong = ProfileActivity.UserDB.getInstance().getString("dong","")
                val writer = ProfileActivity.UserDB.getInstance().getString("user","") ?: ""

                val db = Firebase.firestore
                val testData = hashMapOf(
                    "title" to title,
                    "content" to content,
                    "category" to category,
                    "writer" to writer,
                    "si" to si,
                    "dong" to dong
                )
                db.collection("게시물").document(writer+"_"+title).set(testData)
                //Log.d("해시키", keyHash.toString())

                val intent = Intent(this, InfoListActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_down_enter, R.anim.slide_down_exit);
                finish()
            }
        }
    }

}