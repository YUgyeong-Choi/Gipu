package com.example.gipu_android

import android.content.Intent
import android.os.Bundle
import android.text.TextWatcher
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.gipu_android.databinding.WritingActivityBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import android.Manifest
import android.app.Activity
import android.net.Uri
import android.os.Handler
import android.util.Log
import com.google.firebase.storage.FirebaseStorage

class WritingActivity: AppCompatActivity() {
    private val binding by lazy{
        WritingActivityBinding.inflate(layoutInflater)
    }

    val IMAGE_PICK=1111
    var selectImage: Uri?=null
    var firebaseUrl: String? = null

    var title: String? = null
    var content: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



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
            ActivityCompat.requestPermissions(this@WritingActivity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)

            if(binding.writingContent.text.isNotBlank() && binding.writingContent.text.isNotBlank()){
                var intent = Intent(Intent.ACTION_PICK)
                intent.type="image/*"
                startActivityForResult(intent, IMAGE_PICK)
            }else{
                val toast = Toast.makeText(this@WritingActivity, "제목과 본문을 작성해주세요", Toast.LENGTH_SHORT)
                toast.show()
            }
        }


        var category = "null"

        binding.writingGive.setOnClickListener {
            binding.writingGive.setBackgroundResource(R.drawable.background_give)
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
                val toast = Toast.makeText(this@WritingActivity, "기부 종류를 설정해주세요", Toast.LENGTH_SHORT)
                toast.show()
            }else{
                val si = ProfileActivity.UserDB.getInstance().getString("si","")
                val dong = ProfileActivity.UserDB.getInstance().getString("dong","")
                val writer = ProfileActivity.UserDB.getInstance().getString("user","") ?: ""


                if (selectImage != null) {
                    ProfileActivity.UserDB.init(this)
                    val userName = ProfileActivity.UserDB.getInstance().getString("user", "")

                    val storage = FirebaseStorage.getInstance()
                    val fileName = userName + "_" + binding.writingTitle.text
                    storage.getReference().child("PostImage").child(fileName)
                        .putFile(selectImage!!)
                        .addOnSuccessListener { taskSnapshot ->
                            // 업로드 정보를 담는다
                            taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { imageUrl ->
                                firebaseUrl = imageUrl.toString()


                                val db = Firebase.firestore
                                val testData = hashMapOf(
                                    "imageUrl" to firebaseUrl,
                                    "title" to title,
                                    "content" to content,
                                    "category" to category,
                                    "writer" to writer,
                                    "si" to si,
                                    "dong" to dong
                                )
                                db.collection("게시물").document(writer+"_"+title).set(testData)
                                //Log.d("해시키", keyHash.toString())
                            }
                        }
                }else{
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
                }


                val handler = Handler()
                handler.postDelayed({
                    val intent = Intent(this, InfoListActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_down_enter, R.anim.slide_down_exit)
                    finish()
                }, 2000)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==IMAGE_PICK &&resultCode== Activity.RESULT_OK){
            selectImage=data?.data
            val toast = Toast.makeText(this@WritingActivity, "이미지를 저장했습니다", Toast.LENGTH_SHORT)
            toast.show()
        }
    }

}