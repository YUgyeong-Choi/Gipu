package com.example.gipu_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import com.example.gipu_android.databinding.RegionsettingActivityBinding

class RegionSettingActivity:AppCompatActivity() {
    private val binding by lazy{
        RegionsettingActivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val checkBoxArray = AllCheckboxes(binding.linearSetting)
        //Log.d("체크박스", checkBoxArray.toString())
        for (checkBox in checkBoxArray) {
            checkBox.setOnClickListener { view ->
                handleCheckBoxClick(view as CheckBox, checkBoxArray)
            }
        }

        binding.regionFinishbtn.setOnClickListener {
            var selectedCheckbox: CheckBox? = null

            for (checkBox in checkBoxArray) {
                if (checkBox.isChecked) {
                    selectedCheckbox = checkBox
                    break // 하나만 선택하도록 하기 위해 루프 종료
                }
            }

            // FoodBankListActivity로 이동하는 Intent 생성
            val intent = Intent(this, FoodbankListActivity::class.java)
            // 선택한 체크박스의 텍스트를 인텐트에 추가
            if (selectedCheckbox != null) {
                val selectedText = selectedCheckbox.text.toString()
                intent.putExtra("selectedText", selectedText)
                Log.d("선택된 체크박스", selectedText)
            }
            startActivity(intent)
            finish()
        }

    }

    private fun AllCheckboxes(parentView: View): List<CheckBox>{
        val checkBoxArray = mutableListOf<CheckBox>()
        if (parentView is ViewGroup) {
            for (i in 0 until parentView.childCount) {
                val childView = parentView.getChildAt(i)
                if (childView is CheckBox) {
                    checkBoxArray.add(childView)
                } else if (childView is ViewGroup) {
                    checkBoxArray.addAll(AllCheckboxes(childView))
                }
            }
        }
        return checkBoxArray
    }

    private fun handleCheckBoxClick(clickedCheckBox: CheckBox, checkBoxArray: List<CheckBox>) {
        for (checkBox in checkBoxArray) {
            if (checkBox != clickedCheckBox) {
                checkBox.isChecked = false
            }
        }
    }
}