package com.example.sealed_secondhand

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.example.sealed_secondhand.db.FirebaseAuthentication

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseauth: FirebaseAuthentication
    private lateinit var scene1 : Scene
    private lateinit var loginButton : Button
    private lateinit var signupButton : Button
    private lateinit var idEditText : EditText
    private lateinit var pwEditText : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseauth = FirebaseAuthentication.getInstance(this)
        val sceneRoot = findViewById<FrameLayout>(R.id.scene_root)
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_login_scene1, this)
        var transition: Transition = TransitionInflater.from(this).inflateTransition(R.transition.transition1)

        val thread = Thread {
            Thread.sleep(100)
            runOnUiThread {
                TransitionManager.go(scene1, transition)
                idEditText = findViewById(R.id.idEditText)
                pwEditText = findViewById(R.id.pwEditText)
                loginButton = findViewById(R.id.loginButton)
                loginButton.setOnClickListener {
                    if (idEditText.text.toString() != "" && pwEditText.text.toString() != "") {
                        FirebaseAuthentication.doLogin(idEditText.text.toString(), pwEditText.text.toString())
                            .addOnCompleteListener(this) {
                                if (it.isSuccessful) {
                                    FirebaseAuthentication.setCurrentUser()
                                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(applicationContext, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                    } else {
                        Toast.makeText(this, "ID 및 PASSWORD를 입력해주세요.", Toast.LENGTH_SHORT).show()
                    }
                }
                signupButton = findViewById(R.id.signupButton)
                signupButton.setOnClickListener {
                    val intent = Intent(applicationContext, SignupActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
        }
        thread.start()
    }
}

//                    // 회원가입 관련 코드
//                    if (idEditText.text.toString() != "" && pwEditText.text.toString() != "") {
//                        FirebaseAuthentication.doSignUp(idEditText.text.toString(), pwEditText.text.toString())
//                            .addOnCompleteListener(this) {
//                                if (it.isSuccessful) {
//                                    Toast.makeText(this, "SignUp Success, 로그인을 진행해 주세요.", Toast.LENGTH_SHORT).show()
//                                    idEditText.setText("")
//                                    pwEditText.setText("")
//                                } else {
//                                    Toast.makeText(this, "SignUp Failed", Toast.LENGTH_SHORT).show()
//                                }
//                            }
//                    } else {
//                        Toast.makeText(this, "ID 및 PASSWORD를 입력해주세요.", Toast.LENGTH_SHORT).show()
//                    }