package com.example.gipu_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gipu_android.databinding.FoodbanklistActivityBinding
import com.example.gipu_android.databinding.InfolistActivityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodbankListActivity : AppCompatActivity() {
    private val binding by lazy{
        FoodbanklistActivityBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.foodbankDocument.setOnClickListener {
            val intent = Intent(this, InfoListActivity::class.java)
            startActivity(intent)
            finish()
        }

        val FoodbankList = arrayListOf(
            FoodBankData("진주", "유경마켓", "010-1234-5678", "경상남도 진주시", "비밀","숙박","유경교"),
            FoodBankData("진주", "유경마켓", "010-1234-5678", "경상남도 진주시", "비밀","숙박","유경교"),
            FoodBankData("진주", "유경마켓", "010-1234-5678", "경상남도 진주시", "비밀","숙박","유경교"),
            FoodBankData("진주", "유경마켓", "010-1234-5678", "경상남도 진주시", "비밀","숙박","유경교"),
            FoodBankData("진주", "유경마켓", "010-1234-5678", "경상남도 진주시", "비밀","숙박","유경교"),
            FoodBankData("진주", "유경마켓", "010-1234-5678", "경상남도 진주시", "비밀","숙박","유경교")
        )

        val foodbank_rv = findViewById<RecyclerView>(R.id.foodbank_recyclerView)
        foodbank_rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        foodbank_rv.setHasFixedSize(true)
        foodbank_rv.adapter = FoodbankListAdapter(FoodbankList)

//        val service = CenterInfoImpl.service_ct_tab
//        val call = service.requestList(
//            serviceKey = SERVICEKEY,
//            stdrYm = "202208",
//            numOfRows = "10000",
//            pageNo = "1",
//            dataType = "json",
//            spctrStscd = "1",
//            unitySignguCd = "16020000"
//        )
//
//        call.enqueue(object : Callback<CenterResponse> {
//            override fun onResponse(
//                call: Call<CenterResponse>,
//                response: Response<CenterResponse>
//            ) {
//                if (response.isSuccessful) {
//                    val apiResponse = response.body()
//                    Log.d("response", response.toString())
//                    if (apiResponse != null) {
//                        Log.d("apiResponse", apiResponse.toString())
//                    }
//
//                }else {
//                    // 서버 응답 실패 처리
//                    Log.e("Response", "Response is not successful. Code: ${response.headers()}")
//                }
//            }
//
//            override fun onFailure(call: Call<CenterResponse>, t: Throwable) {
//                // 네트워크 오류 처리
//                Log.e("Response", "Network error: ${t.message}")
//            }
//        })
    }
}