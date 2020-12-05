package com.example.umd_study_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO: Add login code to this activity (at the end of the day the DashboardActivity should
        //  be started) up to you if it makes more sense to pass user id as an extra to the intent or
        //  retrieve it in the dashboard activity like i have it set up now

        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
    }
}