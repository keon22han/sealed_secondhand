package com.example.sealed_secondhand.db.models

import androidx.fragment.app.Fragment

class ChatLog (content: String){
    private lateinit var content: String
    init {
        this.content = content
    }
    fun getContent(): String {
        return content
    }

    fun setContent(content: String) {
        this.content = content
    }
}