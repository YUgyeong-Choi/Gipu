package com.example.gipu_android

import java.io.Serializable

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
    val who : String,
    val useCount : String
)