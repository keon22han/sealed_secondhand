package com.example.sealed_secondhand.db.models

// TODO: 게시글(게시한 유저의 uid를 가지고 있음) 옆에 채팅 버튼 눌렀을 때 user1 -> 채팅을 건 유저, user2 -> 게시글을 올린 유저
class ChatListModel {
    lateinit var destUid: String
    lateinit var chatTitle: String
    lateinit var itemImageUrl: String
    lateinit var lastChat: String
    lateinit var chatRoomId: String
    lateinit var postId: String
}