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
        val joinClassCodeView = findViewById<EditText>(R.id.joinClassCodeView)
        val joinClassSubmitButton = findViewById<Button>(R.id.joinClassSubmitButton)
        var userId = intent.getStringExtra("userId")
        var userClassList = intent.extras?.get("userClassList") as ArrayList<String>

        joinClassSubmitButton.setOnClickListener {
            var code = joinClassCodeView.text.toString()
            Log.i(TAG, "Join Class: $code")
            // TODO: join class on database with "code" as code and "userId" as user id NOTE: make sure that adding to database causes classview to update (timing may get weird causing need to wait for response that writing was successful)

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