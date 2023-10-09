package com.example.gipu_android

class Model(private var imageUrl: String? = null) {

    fun getImageUrl(): String? {
        return imageUrl
    }

    fun setImageUrl(imageUrl: String?) {
        this.imageUrl = imageUrl
    }
}