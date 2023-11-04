package com.example.sealed_secondhand

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.Scene
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.Transition
import androidx.transition.TransitionInflater
import androidx.transition.TransitionManager
import com.google.android.material.transition.MaterialContainerTransform.FadeMode

class LoginActivity : AppCompatActivity() {
    private lateinit var scene1 : Scene
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val sceneRoot = findViewById<FrameLayout>(R.id.scene_root)
        scene1 = Scene.getSceneForLayout(sceneRoot, R.layout.activity_login_scene1, this)
        var fadeTransition: Transition = Fade()
        var transition: Transition = TransitionInflater.from(this).inflateTransition(R.transition.transition1)
        //TransitionManager.go(scene1, transition)

        val thread = Thread {
            Thread.sleep(100)
            runOnUiThread {
                TransitionManager.go(scene1, transition)
            }
        }
        thread.start()
    }
}