package com.example.gipu_android

import kotlin.collections.HashMap

class ChatModel (
    val users: HashMap<String, Boolean> = HashMap(),
    val comments : HashMap<String, Comment> = HashMap()
)
class Comment(val roomName: String? = null, val uid: String? = null, val message: String? = null, val time: String? = null)
