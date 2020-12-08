package com.example.umd_study_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class NotesActivity : AppCompatActivity() {
    private lateinit var userId: String
    private lateinit var className: String
    private lateinit var notesText: EditText
    private lateinit var classText: TextView
    private lateinit var classId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        notesText = findViewById<EditText>(R.id.notesTextBox)
        classText = findViewById<TextView>(R.id.textView)

        classId = intent.extras?.get("classId") as String
        className = intent.extras?.get("className") as String

        classText.text = className

        FirebaseDatabase.getInstance().getReference("Classes").child(classId!!).child("notes").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i(DashboardActivity.TAG, "CLASS DATABASE OBJECT CHANGED: $classId")
                var tempString = dataSnapshot.getValue(String::class.java)
                notesText.setText(tempString)
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e(DashboardActivity.TAG, "FAILED TO READ CLASS DATABASE")
            }
        })
    }

    fun save(view: View ){
        var classNotesRef = FirebaseDatabase.getInstance().getReference("Classes").child(classId!!).child("notes")
        classNotesRef.setValue(notesText.text.toString())

        finish()
    }
}