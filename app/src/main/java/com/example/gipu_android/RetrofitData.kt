package com.example.gipu_android

data class Header(
    val resultCode: String,
    val resultMsg: String
)

// 센터 정보 데이터 클래스
data class CenterResponse(
    val response: CenterResponseBody
)

data class CenterResponseBody(
    val header: Header,
    val body: CenterBody
)

data class CenterBody(
    val dataType: String,
    val items: List<CenterItem>,
    val pageNo: Int,
    val numOfRows: Int,
    val totalCount: Int
)

data class CenterItem(
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

// 이용 시설 단체 데이터 클래스
data class fcltyGrpResponse(
    val response : fcltyGrpResonseBody
)

data class fcltyGrpResonseBody(
    val header: Header,
    val body: fcltyGrpBody
)

data class fcltyGrpBody(
    val dataType: String,
    val items: List<fcltyGrpItem>,
    val pageNo: Int,
    val numOfRows: Int,
    val totalCount: Int
)

data class fcltyGrpItem(
    val stdrYm: String,
    val areaCd: String,
    val unitySignguCd: String,
    val spctrCd: String,
    val fcltySeNm: String,
    val fcltyGrpClscd: String,
    val useAmt: String,
    val useCo: String,
    val fcltyGrpCo: String
)

// 선호 물품 데이터 클래스
data class PreferResponse(
    val response: PreferResponseBody
)

data class PreferResponseBody(
    val header: Header,
    val body: PreferBody
)

data class PreferBody(
    val dataType: String,
    val items: List<PreferItem>,
    val pageNo: Int,
    val numOfRows: Int,
    val totalCount: Int
)
data class PreferItem(
    val areaCd: String,
    val unitySignguCd: String,
    val spctrCd: String,
    val preferCnttgClscd: String,
    val holdQy: String
)