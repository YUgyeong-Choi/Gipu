package com.example.gipu_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gipu_android.databinding.InfolistActivityBinding
import com.example.gipu_android.databinding.WritingActivityBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class WritingActivity: AppCompatActivity() {
    private val binding by lazy{
        WritingActivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


//        val db = Firebase.firestore
//        val testData = hashMapOf(
//            "name" to "yugyeong",
//            "age" to "24",
//            "country" to "korea"
//        )
//
//        db.collection("게시물").add(testData)

    }
}