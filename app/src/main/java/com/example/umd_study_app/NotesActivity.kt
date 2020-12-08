package com.example.umd_study_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
//import android.content.SharedPreferences
import android.view.View
import android.widget.EditText
import android.widget.TextView
import java.io.File

class NotesActivity : AppCompatActivity() {
    private lateinit var userId: String
    private lateinit var className: String
    private lateinit var notesText: EditText
    private lateinit var classText: TextView
    private lateinit var notesStr : String
    private lateinit var classId : String
    lateinit var sharedpreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        //sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE)

        notesText = findViewById<EditText>(R.id.notesTextBox)
        classText = findViewById<TextView>(R.id.textView)

        classId = intent.extras?.get("classId") as String
        className = intent.extras?.get("className") as String

        classText.text = className

        editor = sharedpreferences.edit()
        if(!sharedpreferences.contains(classId+userId))
            editor.putString(classId+userId, "").apply()
        notesStr = sharedpreferences.getString(classId+userId, "").toString()
        notesText.setText(notesStr)
    }
    fun save(view: View ){
        editor.putString(classId+userId, notesText.text.toString()).apply()
        val intent1 = Intent(this, ClassViewActivity::class.java).apply {
            putExtra("classId",classId)
            putExtra("className", className)
            putExtra("classNotes",intent.extras?.get("classNotes") as HashMap<String, String>)
            putExtra("classFlashcards", intent.extras?.get("flashcards") as HashMap<String, Array<String>>)
            putExtra("classResources", intent.extras?.get("resources") as HashMap<String, File>)
            putExtra("userId", userId)
        }
        startActivity(intent1)
    }
}