package com.example.sealed_secondhand

import android.os.Bundle
import android.os.Handler
import android.content.Intent
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val loadingImg: ImageView = findViewById(R.id.startImage)
        Glide.with(this).load(R.raw.initial_image).into(loadingImg)

        Handler().postDelayed({
            val intent = Intent(applicationContext, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3초 후에 MainActivity로 이동
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}