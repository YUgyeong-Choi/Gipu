package com.example.gipu_android

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

var gson= GsonBuilder().setLenient().create()

object CenterInfoImpl{
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    //인증탭 - 메인
    val service_ct_tab : CenterInfoInterface = retrofit.create(CenterInfoInterface::class.java)
}

object FcltyGrpInfoImpl{
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    //인증탭 - 메인
    val service_ct_tab : FcltyGrpInfoInterface = retrofit.create(FcltyGrpInfoInterface::class.java)
}

object PreferInfoImpl{
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(Url)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
    //인증탭 - 메인
    val service_ct_tab : PreferInfoInterface = retrofit.create(PreferInfoInterface::class.java)
}