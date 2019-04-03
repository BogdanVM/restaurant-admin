package com.gr232.restaurantadmin

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val animationDrawable : AnimationDrawable = loginActivityLayout.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(3500)
        animationDrawable.setExitFadeDuration(3500)
        animationDrawable.start()
    }
}