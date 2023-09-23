package com.example.gipu_android

data class InfoData(
    val category : String,
    val title : String,
    val content : String,
    val location : String
)

data class FoodBankData(
    val region : String,
    val name : String,
    val telephone : String,
    val location : String,
    val locationDetail : String,
    val what : String,
    val who : String
)