package com.example.sealed_secondhand

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {
    private lateinit var pictureFloatButton : com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var chattingFloatButton : com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var plusFloatButton : com.google.android.material.floatingactionbutton.FloatingActionButton

    private var isFloatOpen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pictureFloatButton = findViewById(R.id.pictureFloatButton)
        chattingFloatButton = findViewById(R.id.chattingFloatButton)
        plusFloatButton = findViewById(R.id.plusFloatButton)

        // TODO: setOnClickListener 작성
        plusFloatButton.setOnClickListener {
            toggleFloat()
        }
        chattingFloatButton.setOnClickListener {
            replaceFragment(ChatListActivity(this))
        }
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out,
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out
            )
            .add(R.id.fragment_container, PostListActivity(this))
            //.add(R.id.fragment_container, ChatActivity("에어팟팔아용", "1971193", "keonheehan"))
            .addToBackStack(null)
            .commit()
    }

    private fun toggleFloat() {
        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션
        if (isFloatOpen) {
            ObjectAnimator.ofFloat(pictureFloatButton, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(chattingFloatButton, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(plusFloatButton, View.ROTATION, 45f, 0f).apply { start() }
        } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션
            ObjectAnimator.ofFloat(pictureFloatButton, "translationY", -360f).apply { start() }
            ObjectAnimator.ofFloat(chattingFloatButton, "translationY", -180f).apply { start() }
            ObjectAnimator.ofFloat(plusFloatButton, View.ROTATION, 0f, 45f).apply { start() }
        }
        isFloatOpen = !isFloatOpen
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out,
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out
            )
            .add(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }
}