package com.example.sealed_secondhand

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.sealed_secondhand.db.FirebaseAuthentication
import com.example.sealed_secondhand.db.models.User
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        val confirmButton = findViewById<Button>(R.id.signupCompleteButton)
        confirmButton.setOnClickListener {
            var user: User = User()
            user.userId = findViewById<EditText>(R.id.signupIdTextView).text.toString()
            user.userPW = findViewById<EditText>(R.id.signupPWTextView).text.toString()
            user.birthDay = findViewById<DatePicker>(R.id.signupDatePicker).year.toString() + "." + (findViewById<DatePicker>(R.id.signupDatePicker).month+1).toString() + "." + findViewById<DatePicker>(R.id.signupDatePicker).dayOfMonth.toString()
            user.nickName = findViewById<EditText>(R.id.signupNameTextView).text.toString()
            user.profileImgUrl = ""
            if(user.userId=="" || user.userPW=="" || user.nickName=="") {
                Toast.makeText(applicationContext, "입력하지 않은 내용이 있습니다.", Toast.LENGTH_SHORT).show()
            }
            else {
                FirebaseAuthentication.doSignUp(user.userId, user.userPW)
                    .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "SignUp Success, 로그인을 진행해 주세요.", Toast.LENGTH_SHORT).show()
                            val firebaseUser = it.result?.user
                            val uid = firebaseUser?.uid
                            user.firebaseUserId = uid.toString()
                            FirebaseDatabase.getInstance().reference.child("User").push()
                                .setValue(user)
                            val intent = Intent(applicationContext, LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this, "SignUp Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}