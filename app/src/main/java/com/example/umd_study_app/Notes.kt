package com.example.umd_study_app

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class NoteActivity : AppCompatActivity() {
    private lateinit var userId: String
    private lateinit var className: String
    private lateinit var notesText: EditText
    private lateinit var classText: TextView
    private lateinit var notesStr : String
    lateinit var sharedpreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes_view)
        sharedpreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE)
        notesText = findViewById<EditText>(R.id.editText)
        classText = findViewById<TextView>(R.id.textView)
        userId =  intent.extras?.get("userId") as String
        className = intent.extras?.get("className") as String
        classText.text = className
        editor = sharedpreferences.edit()
        if(!sharedpreferences.contains(className+userId))
            editor.putString(className+userId, "").apply()
        notesStr = sharedpreferences.getString(className+userId, "").toString()
        notesText.setText(notesStr)

    }
    fun save(view: View){
        editor.putString(className+userId, notesText.text.toString()).apply()
    }

}
