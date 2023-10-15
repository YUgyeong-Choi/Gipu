package com.example.gipu_android

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.gipu_android.databinding.FoodbanklistActivityBinding
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
            overridePendingTransition(0, 0);
            finish()
        }

        binding.foodbankProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0);
            finish()
        }

        binding.foodbankChat.setOnClickListener {
            val intent = Intent(this, ChatListActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0);
            finish()
        }

        binding.regionSettingbtn.setOnClickListener {
            val intent = Intent(this, FoodbankRegionSettingActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_right_enter, R.anim.slide_right_exit)
            finish()
        }

        val selectedText = intent.getStringExtra("selectedText")
        var selectedKey= "16"
        for ((key, value) in regionData.data) {
            if (value == selectedText) {
                selectedKey = key
                break
            }
        }

        if (selectedKey == "16"){
            callAllData(selectedKey)
        }else{
            callRegionData(selectedKey)
        }
    }
    private fun callAllData(selectedKey: String){
        var FoodbankList = arrayListOf<FoodBankData>()

        val service = AllCenterInfoImpl.service_ct_tab
        val call = service.requestList(
            serviceKey = SERVICEKEY,
            stdrYm = "202202",
            numOfRows = "10000",
            pageNo = "1",
            dataType = "json",
            spctrStscd = "1",
            areaCd = selectedKey
        )

        call.enqueue(object : Callback<CenterResponse> {
            override fun onResponse(
                call: Call<CenterResponse>,
                response: Response<CenterResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("response", response.toString())
                    if (apiResponse != null) {
                        Log.d("apiResponse", apiResponse.toString())

                        val foodbankInfos = apiResponse.response.body.items

                        for (foodbankInfo in foodbankInfos) {
                            var foodbank = FoodBankData(
                                region = foodbankInfo.unitySignguCd,
                                name = foodbankInfo.spctrCd,
                                telephone = foodbankInfo.spctrTelno,
                                location = foodbankInfo.spctrAdres,
                                locationDetail = foodbankInfo.spctrDetailAdres,
                                who = foodbankInfo.operMbySclasCd,
                                useCount = foodbankInfo.userCo
                            )
                            FoodbankList.add(foodbank)
                        }

                        val foodbank_rv = findViewById<RecyclerView>(R.id.foodbank_recyclerView)
                        foodbank_rv.layoutManager = LinearLayoutManager(this@FoodbankListActivity, LinearLayoutManager.VERTICAL, false)
                        foodbank_rv.setHasFixedSize(true)
                        foodbank_rv.adapter = FoodbankListAdapter(FoodbankList)

                    }

                }else {
                    // 서버 응답 실패 처리
                    Log.e("Response", "Response is not successful. Code: ${response.headers()}")
                }
            }

            override fun onFailure(call: Call<CenterResponse>, t: Throwable) {
                // 네트워크 오류 처리
                Log.e("Response", "Network error: ${t.message}")
            }
        })
    }

    private fun callRegionData(selectedKey: String){
        var FoodbankList = arrayListOf<FoodBankData>()

        val service = CenterInfoImpl.service_ct_tab
        val call = service.requestList(
            serviceKey = SERVICEKEY,
            stdrYm = "202202",
            numOfRows = "10000",
            pageNo = "1",
            dataType = "json",
            spctrStscd = "1",
            unitySignguCd = selectedKey
        )

        call.enqueue(object : Callback<CenterResponse> {
            override fun onResponse(
                call: Call<CenterResponse>,
                response: Response<CenterResponse>
            ) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    Log.d("response", response.toString())
                    if (apiResponse != null) {
                        Log.d("apiResponse", apiResponse.toString())

                        val foodbankInfos = apiResponse.response.body.items

                        for (foodbankInfo in foodbankInfos) {
                            var foodbank = FoodBankData(
                                region = foodbankInfo.unitySignguCd,
                                name = foodbankInfo.spctrCd,
                                telephone = foodbankInfo.spctrTelno,
                                location = foodbankInfo.spctrAdres,
                                locationDetail = foodbankInfo.spctrDetailAdres,
                                who = foodbankInfo.operMbySclasCd,
                                useCount = foodbankInfo.userCo
                            )
                            FoodbankList.add(foodbank)
                        }

                        val foodbank_rv = findViewById<RecyclerView>(R.id.foodbank_recyclerView)
                        foodbank_rv.layoutManager = LinearLayoutManager(this@FoodbankListActivity, LinearLayoutManager.VERTICAL, false)
                        foodbank_rv.setHasFixedSize(true)
                        foodbank_rv.adapter = FoodbankListAdapter(FoodbankList)

                    }

                }else {
                    // 서버 응답 실패 처리
                    Log.e("Response", "Response is not successful. Code: ${response.headers()}")
                }
            }

            override fun onFailure(call: Call<CenterResponse>, t: Throwable) {
                // 네트워크 오류 처리
                Log.e("Response", "Network error: ${t.message}")
            }
        })
    }
}