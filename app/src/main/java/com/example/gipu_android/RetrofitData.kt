package com.example.gipu_android

data class CenterResponse(
    val response: ResponseBody
)

data class ResponseBody(
    val header: Header,
    val body: Body
)

data class Header(
    val resultCode: String,
    val resultMsg: String
)

data class Body(
    val dataType: String,
    val items: List<Item>,
    val pageNo: Int,
    val numOfRows: Int,
    val totalCount: Int
)

data class Item(
    val stdrYm: String,
    val areaCd: String,
    val unitySignguCd: String,
    val spctrUncd: String,
    val spctrSecd: String,
    val spctrCd: String,
    val spctrTelno: String,
    val spctrAdres: String,
    val spctrDetailAdres: String,
    val spctrStscd: String,
    val operMbyLclasCd: String,
    val operMbySclasCd: String,
    val sttemntSeccd: String,
    val registDe: String,
    val rceptAmt: String,
    val provdAmt: String,
    val undtakeAmt: String,
    val trnsferAmt: String,
    val userCo: String
)



