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

        val createClassNameView = findViewById<EditText>(R.id.createClassNameView)
        val createClassSubmitButton = findViewById<Button>(R.id.createClassSubmitButton)
        var userId = intent.getStringExtra("userId")
        var userClassList = intent.extras?.get("userClassList") as ArrayList<String>

        createClassSubmitButton.setOnClickListener {
            var name = createClassNameView.text.toString()
            Log.i(TAG, "Create Class: $name")
            // TODO: send new class to database with "name" as name and userId as user id NOTE: make
            //  sure that adding to database causes classview to update (timing may get weird causing
            //  need to wait for response that writing was successful)
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