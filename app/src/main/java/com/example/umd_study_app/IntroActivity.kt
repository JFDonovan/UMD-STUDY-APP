package com.example.umd_study_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.graphics.drawable.Drawable
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat

class IntroActivity : AppCompatActivity() {
    private var registerBtn: Button? = null
    private var loginBtn: Button? = null
    private var umdImg: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        initializeViews()

        registerBtn!!.setOnClickListener {
            val intent = Intent(this@IntroActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }
        loginBtn!!.setOnClickListener {
            val intent = Intent(this@IntroActivity, LoginActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initializeViews() {
        registerBtn = findViewById(R.id.register)
        loginBtn = findViewById(R.id.login)
        umdImg = findViewById(R.id.image)
    }
}