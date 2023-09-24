package com.example.gipu_android

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.gipu_android.databinding.FoodbankDetailpopBinding
import com.google.gson.Gson

class FoodbankDetailActivity(val getData : FoodBankData, private val context: Context) {

    fun show() {
        val builder = AlertDialog.Builder(context)

        // 다이얼로그의 제목 설정
        builder.setTitle("상세 정보")

        // 다이얼로그의 내용 설정
        val message = buildMessage()
        builder.setMessage(message)

        // 확인 버튼 설정
        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss() // 다이얼로그를 닫음
        }

        // 다이얼로그 생성 및 표시
        val dialog = builder.create()
        dialog.show()
    }

    private fun buildMessage(): String {
        val stringBuilder = StringBuilder()
        val whatCenter_word = "시설단체 분류 :  시설\n"
        val useCount_word = "이용자 수 : " + getData.useCount +"\n"
        val who = "운영주체 분류 :  " + getData.who +"\n"
        stringBuilder.append(whatCenter_word)
        stringBuilder.append(useCount_word)
        stringBuilder.append(who)

        return stringBuilder.toString()
    }
}





