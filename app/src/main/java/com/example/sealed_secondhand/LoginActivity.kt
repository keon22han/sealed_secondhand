package com.example.sealed_secondhand

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Scene
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.example.sealed_secondhand.db.FirebaseAuth
import com.google.android.material.transition.MaterialContainerTransform.FadeMode

class LoginActivity : AppCompatActivity() {
    private lateinit var firebaseauth: FirebaseAuth
    private lateinit var scene1 : Scene
    private lateinit var loginButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        firebaseauth = FirebaseAuth.getInstance(this)
        val sceneRoot = findViewById<FrameLayout>(R.id.scene_root)
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_login_scene1, this)
        var fadeTransition: Transition = Fade()
        var transition: Transition = TransitionInflater.from(this).inflateTransition(R.transition.transition1)

        val thread = Thread {
            Thread.sleep(100)
            runOnUiThread {
                TransitionManager.go(scene1, transition)
                loginButton = findViewById(R.id.loginButton)
                loginButton.setOnClickListener {
                    FirebaseAuth.doLogin(findViewById<EditText>(R.id.idEditText).text.toString(), findViewById<EditText>(R.id.pwEditText).text.toString())
                        .addOnCompleteListener(this) {
                        if (it.isSuccessful) {
                            Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else {
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        thread.start()
    }
}