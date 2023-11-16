package com.example.sealed_secondhand

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.sealed_secondhand.db.models.PostListModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var pictureFloatButton : com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var chattingFloatButton : com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var plusFloatButton : com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var logoutButton : Button

    private lateinit var postListActivity: PostListActivity
    private lateinit var filteringAllButton : Button
    private lateinit var filteringSalesButton : Button
    private lateinit var filteringSaledButtoon : Button

    private var isFloatOpen = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        postListActivity = PostListActivity(this)

        pictureFloatButton = findViewById(R.id.pictureFloatButton)
        chattingFloatButton = findViewById(R.id.chattingFloatButton)
        plusFloatButton = findViewById(R.id.plusFloatButton)

        // TODO: setOnClickListener 작성
        plusFloatButton.setOnClickListener {
            toggleFloat()
        }

        pictureFloatButton.setOnClickListener {
            replaceFragment(PostActivity(this@MainActivity, "", PostListModel(), true))
        }

        chattingFloatButton.setOnClickListener {
            replaceFragment(ChatListActivity(this))
        }

        // BottomNavigationView 인스턴스를 찾습니다.
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    replaceFragment(PostListActivity(this))
                    true
                }
                R.id.navigation_chat -> {
                    replaceFragment(ChatListActivity(this))
                    true
                }
                R.id.navigation_user -> {
                    //replaceFragment(UserActivity())
                    true
                }
                else -> false
            }
        }

        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out,
                androidx.appcompat.R.anim.abc_fade_in,
                androidx.appcompat.R.anim.abc_fade_out
            )
            .add(R.id.fragment_container, postListActivity)
            //.add(R.id.fragment_container, ChatActivity("에어팟팔아용", "1971193", "keonheehan"))
            .addToBackStack(null)
            .commit()

        logoutButton = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            changeActivity(LoginActivity())
        }
        changeMainBarText("sealed_secondhand")

        // 필터링을 위한 clicklistener 작성
        filteringAllButton = findViewById(R.id.showAllProduct)
        filteringSalesButton = findViewById(R.id.showSalesProduct)
        filteringSaledButtoon = findViewById(R.id.showSaledProduct)

        filteringAllButton.setOnClickListener {
            postListActivity.checkPostList(null)
        }

        filteringSalesButton.setOnClickListener {
            postListActivity.checkPostList(true)
        }

        filteringSaledButtoon.setOnClickListener {
            postListActivity.checkPostList(false)
        }
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
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    fun popBackStack() {
        supportFragmentManager.popBackStack()
        if(supportFragmentManager.backStackEntryCount == 0)
            finish()
    }

    fun changeActivity(activity: Activity) {
        val intent = Intent(applicationContext, activity::class.java)
        startActivity(intent)
        finish()
    }

    fun changeMainBarText(text: String) {
        findViewById<TextView>(R.id.mainBarTextView).text = text
    }
}