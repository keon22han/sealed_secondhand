package com.example.sealed_secondhand.db.models

// 회원가입 시 유저 데이터 저장
class User {
    lateinit var firebaseUserId: String
    lateinit var nickName: String
    lateinit var profileImgUrl: String
    lateinit var userId: String
    lateinit var userPW: String
    lateinit var birthDay: String
}