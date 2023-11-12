package com.example.sealed_secondhand.db.models

class PostListModel {
    lateinit var postId: String
    lateinit var postTitle: String
    lateinit var postContent: String
    lateinit var itemImageUrl: String
    lateinit var price: String
    lateinit var user: User
    var saleState: Boolean = true


}