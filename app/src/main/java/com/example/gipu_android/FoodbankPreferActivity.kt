package com.example.gipu_android

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FoodbankPreferActivity(val getData : FoodBankData, private val context: Context) {

    fun show() {
        val builder = AlertDialog.Builder(context)

        // 다이얼로그의 제목 설정
        builder.setTitle("선호 물품")

        // 다이얼로그의 내용 설정
        getPreferInfo(getData.name) { fcltydict ->
            val message = buildMessage(fcltydict)
            builder.setMessage(message)

            // 다이얼로그 생성 및 표시
            val dialog = builder.create()
            dialog.show()
        }

        // 확인 버튼 설정
        builder.setPositiveButton("확인") { dialog, _ ->
            dialog.dismiss() // 다이얼로그를 닫음
        }
    }

    private fun buildMessage(preferdict : MutableMap<String, String>): String {
        val stringBuilder = StringBuilder()

        for ((key, value) in preferdict) {
            stringBuilder.append("물품 : " + preferData.data[key]+"\t\t"+ "보유 수량: " + value+"\n")
        }

        return stringBuilder.toString()
    }

    private fun getPreferInfo(name : String, callback: (MutableMap<String, String>) -> Unit) {
        val service = PreferInfoImpl.service_ct_tab
        val call = service.requestList(
            serviceKey = SERVICEKEY,
            numOfRows = "10000",
            pageNo = "1",
            dataType = "json",
            spctrCd = name,
        )

        val preferdict: MutableMap<String, String> = mutableMapOf()

        call.enqueue(object : Callback<PreferResponse> {
            override fun onResponse(
                call: Call<PreferResponse>,
                response: Response<PreferResponse>
            ){
                if (response.isSuccessful){
                    val apiResponse = response.body()
                    Log.d("response", response.toString())
                    if (apiResponse != null){
                        Log.d("apiResponse", apiResponse.toString())

                        val preferInfos = apiResponse.response.body.items

                        for (preferInfo in preferInfos){
                            preferdict[preferInfo.preferCnttgClscd] = preferInfo.holdQy
                        }
                        callback(preferdict)
                    }
                }else{
                    // 서버 응답 실패 처리
                    Log.e("Response", "Response is not successful. Code: ${response.headers()}")
                }
            }
            override fun onFailure(call: Call<PreferResponse>, t: Throwable) {
                // 네트워크 오류 처리
                Log.e("Response", "Network error: ${t.message}")
            }
        })
    }
}
