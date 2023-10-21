package com.example.gipu_android

data class RegionData(val data: Map<String, String>)
data class CenterNameData(val data: Map<String, String>)
data class OrganizationData(val data: Map<String, String>)
data class FcltyGrpData(val data: Map<String, String>)
data class PreferData(val data: Map<String, String>)
data class CenterLocationData(val data: Map<String, Location>)
data class Location(
    val lat: Double,
    val lng: Double
)

val regionData = RegionData(
    mapOf(
        "16000200" to "함안군",
        "16000300" to "창녕군",
        "16000400" to "고성군",
        "16000500" to "남해군",
        "16000600" to "하동군",
        "16000700" to "산청군",
        "16000800" to "함양군",
        "16000900" to "거창군",
        "16001000" to "합천군",
        "16010001" to "창원시 의창구",
        "16010004" to "창원시 마산회원구",
        "16010005" to "창원시 진해구",
        "16020000" to "진주시",
        "16030000" to "통영시",
        "16040000" to "사천시",
        "16050000" to "김해시",
        "16060000" to "밀양시",
        "16070000" to "거제시",
        "16080000" to "양산시"
    )
)

val centerNameData = CenterNameData(
    mapOf(
        "S19201" to "경남광역푸드뱅크",
        "S19301" to "마산푸드뱅크",
        "S19401" to "진주시푸드뱅크",
        "S19501" to "진해푸드뱅크",
        "S19601" to "통영시푸드뱅크",
        "S19701" to "사천시푸드뱅크",
        "S19801" to "김해시푸드뱅크",
        "S19901" to "밀양시푸드뱅크",
        "S20001" to "거제시푸드뱅크",
        "S20101" to "양산시푸드뱅크",
        "S20301" to "함안군푸드뱅크",
        "S20401" to "창녕군푸드뱅크",
        "S20501" to "고성군푸드뱅크",
        "S20601" to "남해군푸드뱅크",
        "S20701" to "하동군푸드뱅크",
        "S20801" to "산청군푸드뱅크",
        "S20901" to "함양군푸드뱅크",
        "S21101" to "합천군푸드뱅크",
        "S26101" to "창원시기초푸드뱅크",
        "S39101" to "김해시푸드마켓",
        "S39801" to "통영나누미푸드마켓",
        "S39901" to "진주푸드마켓",
        "S41601" to "창원시희망푸드마켓",
        "S49201" to "거창푸드뱅크",
        "S63701" to "산청군푸드뱅크",
        "S66701" to "김해시나누미푸드뱅크"
    )
)

val organizationData = OrganizationData(
    mapOf(
        "0101" to "사회복지법인",
        "0102" to "재단법인",
        "0103" to "사단법인",
        "0104" to "종교법인",
        "0105" to "학교법인",
        "0201" to "지방자치단체",
        "0202" to "지방자치단체 소속 기관",
        "0301" to "비영리 민간 단체·시설",
        "0401" to "푸드뱅크·마켓 자체신고운영",
        "0402" to "종교시설·단체",
        "0403" to "기타"
    )
)

val fcltyGrpData = FcltyGrpData(
    mapOf(
        "11" to "무료급식소",
        "12" to "복지관",
        "13" to "재가복지센터",
        "14" to "지역아동센터",
        "15" to "기타이용시설",
        "31" to "결핵한센인시설",
        "32" to "노인시설",
        "33" to "부랑인시설",
        "34" to "아동시설",
        "35" to "여성시설",
        "36" to "영유아시설",
        "37" to "장애인시설",
        "38" to "정신보건시설",
        "39" to "정신요양시설",
        "40" to "청소년쉼터",
        "41" to "한부모가족시설",
        "42" to "기타생활시설",
        "51" to "기타법인단체",
        "52" to "종교사회단체",
        "53" to "　　기타　　　"
    )
)

val preferData = PreferData(
    mapOf(
        "01" to "라면류",
        "02" to "쌀(곡식류)",
        "03" to "밀가루(류)",
        "04" to "고추장(류)",
        "05" to "된장(류)",
        "06" to "참기름",
        "07" to "(즉석)가공햄류",
        "08" to "식용유(류)",
        "09" to "통조림류",
        "10" to "설탕(류)",
        "11" to "간장(류)",
        "12" to "소면류(국수)",
        "13" to "김(류)",
        "14" to "즉석밥류",
        "15" to "샴푸",
        "16" to "치약",
        "17" to "비누"
    )
)

val centerMapData = CenterLocationData(
    mapOf(
        "S19201" to Location(35.185014, 128.835393),
        "S19301" to Location(35.243156, 128.499833),
        "S19401" to Location(35.192293,128.063937),
        "S19501" to Location(35.133604, 128.710303),
        "S19601" to Location(34.857379, 128.4322419),
        "S19701" to Location(34.933301, 128.082122),
        "S19801" to Location(35.228670, 128.886532),
        "S19901" to Location(35.483776, 128.758979),
        "S20001" to Location(34.882310, 128.622923),
        "S20101" to Location(35.332370, 129.010992),
        "S20301" to Location(35.272581, 128.406559),
        "S20401" to Location(35.537572, 128.490009),
        "S20501" to Location(34.986296, 128.332296),
        "S20601" to Location(34.842158, 127.878452),
        "S20701" to Location(35.072155, 127.745200),
        "S20801" to Location(35.4128764,127.8739705),
        "S20901" to Location(35.520824,127.7789918),
        "S21101" to Location(35.5638992,128.1579441),
        "S26101" to Location(35.2553122,128.6407065),
        "S39101" to Location(35.2278843,128.8733746),
        "S39801" to Location(34.858796, 128.431727),
        "S39901" to Location(35.192303,128.0638991),
        "S41601" to Location(35.2271204,128.6870389),
        "S49201" to Location(35.689034,127.8697796),
        "S63701" to Location(35.4128764,127.8739705),
        "S66701" to Location(35.234210, 128.849961)
    )
)