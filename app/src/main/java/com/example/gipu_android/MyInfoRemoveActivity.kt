package com.example.gipu_android

import android.content.Context
import androidx.appcompat.app.AlertDialog

class MyInfoRemoveActivity(private val context: Context) {
    // 다이얼로그의 결과를 처리할 콜백 함수 정의
    interface OnDialogResultListener {
        fun onResult(result: Boolean)
    }

    fun show(callback: OnDialogResultListener) {
        val builder = AlertDialog.Builder(context)

        // 다이얼로그의 제목 설정
        builder.setTitle("경고")
        builder.setMessage("해당 게시물을 정말 삭제하시겠습니까?")

        // 확인 버튼 설정
        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss() // 다이얼로그를 닫음
            callback.onResult(true)
        }
        // 취소 버튼 설정
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
            callback.onResult(false)
        }

        val dialog = builder.create()
        dialog.show()
    }
}