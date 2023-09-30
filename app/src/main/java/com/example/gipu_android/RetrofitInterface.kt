package com.example.gipu_android

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface CenterInfoInterface{
    @GET("getSpctrInfo")
    fun requestList(
        @Query("serviceKey") serviceKey: String,
        @Query("stdrYm") stdrYm: String,
        @Query("numOfRows") numOfRows: String,
        @Query("pageNo") pageNo: String,
        @Query("dataType") dataType: String,
        @Query("spctrStscd") spctrStscd: String,
        @Query("unitySignguCd") unitySignguCd: String
    ): Call<CenterResponse>
}

interface FcltyGrpInfoInterface{
    @GET("getFcltyGrpInfo")
    fun requestList(
        @Query("serviceKey") serviceKey: String,
        @Query("stdrYm") stdrYm: String,
        @Query("numOfRows") numOfRows: String,
        @Query("pageNo") pageNo: String,
        @Query("dataType") dataType: String,
        @Query("spctrCd") spctrCd: String
    ): Call<fcltyGrpResponse>
}

interface PreferInfoInterface{
    @GET("getPreferInfo")
    fun requestList(
        @Query("serviceKey") serviceKey: String,
        @Query("numOfRows") numOfRows: String,
        @Query("pageNo") pageNo: String,
        @Query("dataType") dataType: String,
        @Query("spctrCd") spctrCd: String
    ): Call<PreferResponse>
}