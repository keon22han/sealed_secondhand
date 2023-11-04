package com.example.sealed_secondhand.db

import android.content.Context
import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class FirebaseAuthentication private constructor() {
    companion object {
        private var instance: FirebaseAuthentication? = null

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
            Log.i("hi","doLogin")
            return Firebase.auth.signInWithEmailAndPassword(userEmail, password)
        }

        fun doSignUp(userEmail: String, password: String): Task<AuthResult> {
            return Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
        }
    }
}