package com.example.sealed_secondhand.db.models

class ChatModel {
    var users: Map<String, Boolean> = HashMap()
    var comments: Map<String, Comment> = HashMap()
    class Comment {
        lateinit var uid: String
        lateinit var message: String
        lateinit var timestamp: Any
    }
}