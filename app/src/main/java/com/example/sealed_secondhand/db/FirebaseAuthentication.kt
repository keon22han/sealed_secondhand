package com.example.sealed_secondhand.db

import android.content.Context
import com.example.sealed_secondhand.db.models.ChatModel
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class FirebaseAuthentication private constructor() {
    companion object {
        private var instance: FirebaseAuthentication? = null
        private lateinit var myUid: String
        private lateinit var context: Context
        fun getInstance(_context: Context): FirebaseAuthentication {
            return instance ?: synchronized(this) {
                instance ?: FirebaseAuthentication().also {
                    context = _context
                    instance = it
                }
            }
        }

        fun doLogin(userEmail: String, password: String): Task<AuthResult> {
            return Firebase.auth.signInWithEmailAndPassword(userEmail, password)
        }

        fun doSignUp(userEmail: String, password: String): Task<AuthResult> {
            return Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
        }

        fun setCurrentUser() {
            myUid = FirebaseAuth.getInstance().currentUser!!.uid
        }

        fun getCurrentUser(): String {
            return myUid
        }

        fun getMessageRoot(chatRoomUid: String): DatabaseReference {
            return FirebaseDatabase.getInstance().reference.child("chatRooms").child(chatRoomUid).child("comments")
        }

        fun sendMessageToDataBase(chatRoomUid: String, comment: ChatModel.Comment): Task<Void> {
            return FirebaseDatabase.getInstance().reference.child("chatRooms").child(chatRoomUid).child("comments").push().setValue(comment)
        }

        fun getChatListRoot(): Query {
            return FirebaseDatabase.getInstance().reference.child("chatList").orderByChild("users/$myUid").equalTo(true)
        }
    }
}