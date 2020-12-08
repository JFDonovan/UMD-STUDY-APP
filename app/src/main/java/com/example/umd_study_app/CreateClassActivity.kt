package com.example.umd_study_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.ArrayList

class CreateClassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_class)

        // Get views
        val createClassNameView = findViewById<EditText>(R.id.createClassNameView)
        val createClassSubmitButton = findViewById<Button>(R.id.createClassSubmitButton)

        // Get id and class list as extras
        var userId = intent.getStringExtra("userId")
        var userClassList = intent.extras?.get("userClassList") as ArrayList<String>

        // On Submission write to database
        createClassSubmitButton.setOnClickListener {
            var name = createClassNameView.text.toString()
            Log.i(TAG, "Create Class: $name")
            var classId = UUID.randomUUID().toString()
            var classObj = Class(classId, name, hashMapOf(), hashMapOf(), hashMapOf())
            var classRef = FirebaseDatabase.getInstance().getReference("Classes")
            var userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)

            userClassList.add(classId)
            classRef.child(classId).setValue(classObj)
            userRef.child("classes").setValue(userClassList)

            finish()
        }
    }

    companion object {
        const val TAG = "UMD-STUDY-APP"
    }
}