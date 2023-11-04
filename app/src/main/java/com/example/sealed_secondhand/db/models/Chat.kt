package com.example.sealed_secondhand.db.models

// TODO: ChatRecyclerAdapter와 연결
class Chat : Comparable<Chat> {
    private lateinit var user1: String
    private lateinit var user2: String
    private lateinit var chatTitle: String
    private lateinit var lastChat: String

    // Title 비교 정렬 가능
    override fun compareTo(other: Chat): Int {
        if (this.chatTitle > other.chatTitle) return 1
        else if (this.chatTitle < other.chatTitle) return -1
        else return 0
    }

    fun getUser1(): String {
        return user1
    }

    fun getUser2(): String {
        return user1
    }

    fun getChatTitle(): String {
        return chatTitle
    }

    fun getLastChat(): String {
        return lastChat
    }

    fun setChatTitle(chatTitle: String) {
        this.chatTitle = chatTitle
    }

    fun setlastChat(lastChat: String) {
        this.lastChat = lastChat
    }
}