package com.example.sealed_secondhand.db

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sealed_secondhand.MainActivity
import com.example.sealed_secondhand.R
import com.google.android.gms.tasks.Task
import com.google.android.play.integrity.internal.t
import com.google.firebase.auth.AuthResult
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class FirebaseAuth private constructor() {
    companion object {
        private var instance: FirebaseAuth? = null

        private lateinit var context: Context
        fun getInstance(_context: Context): FirebaseAuth {
            return instance ?: synchronized(this) {
                instance ?: FirebaseAuth().also {
                    context = _context
                    instance = it
                }
            }
        }

        // TODO: AppCompat 딴에서 Auth Result 받고, 성공이라면 MainActivity 넘어가게 하기
        fun doLogin(userEmail: String, password: String): Task<AuthResult> {
            Log.i("hi","doLogin")
            return Firebase.auth.signInWithEmailAndPassword(userEmail, password)
        }
    }
}