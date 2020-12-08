package com.example.umd_study_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase

class JoinClassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_class)
        // Get Views
        val joinClassCodeView = findViewById<EditText>(R.id.joinClassCodeView)
        val joinClassSubmitButton = findViewById<Button>(R.id.joinClassSubmitButton)

        // Getting User ID and Class list from Extras
        var userId = intent.getStringExtra("userId")
        var userClassList = intent.extras?.get("userClassList") as ArrayList<String>

        // On Submission write to database
        joinClassSubmitButton.setOnClickListener {
            var code = joinClassCodeView.text.toString()
            Log.i(TAG, "Join Class: $code")
            var userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)
            userClassList.add(code)
            userRef.child("classes").setValue(userClassList)

            finish()
        }
    }

    companion object {
        const val TAG = "UMD-STUDY-APP"
    }
}