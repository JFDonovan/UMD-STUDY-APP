package com.example.umd_study_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

class CreateClassActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_class)

        val createClassNameView = findViewById<EditText>(R.id.createClassNameView)
        val createClassSubmitButton = findViewById<Button>(R.id.createClassSubmitButton)
        var userId = intent.getStringExtra("userId")

        createClassSubmitButton.setOnClickListener {
            var name = createClassNameView.text
            Log.i(TAG, "Create Class: $name")
            // TODO: send new class to database with "name" as name and userId as user id NOTE: make
            //  sure that adding to database causes classview to update (timing may get weird causing
            //  need to wait for response that writing was successful)

            finish()
        }
    }

    companion object {
        const val TAG = "UMD-STUDY-APP"
    }
}