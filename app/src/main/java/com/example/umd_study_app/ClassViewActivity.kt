package com.example.umd_study_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.io.File

class ClassViewActivity : AppCompatActivity() {
    private var classId: String = ""
    private var className: String = ""
    private lateinit var userId : String
    private var classNotes: HashMap<String, String>? = null
    private var classFlashcards: HashMap<String, Array<String>>? = null
    private var classResources: HashMap<String, File>? = null
    private var username = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_view)

        classId = intent.extras?.get("classId") as String
        className = intent.extras?.get("className") as String
        classNotes = intent.extras?.get("classNotes") as HashMap<String, String>
        classFlashcards = intent.extras?.get("classFlashcards") as HashMap<String, Array<String>>
        classResources = intent.extras?.get("classResources") as HashMap<String, File>
        userId = intent.extras?.get("userId") as String


        val classNameView = findViewById<TextView>(R.id.classNameView)
        val notesButton = findViewById<Button>(R.id.notesButton)
        val flashcardsButton = findViewById<Button>(R.id.flashcardsButton)
        val resourcesButton = findViewById<Button>(R.id.resourcesButton)

        classNameView.text = className
        notesButton.setOnClickListener {
            // TODO: Start notes activity, using classNotes as the data
            intent = Intent(this@ClassViewActivity, NoteActivity::class.java)
            intent.putExtra("userId", userId)
            intent.putExtra("className", className)
            startActivity(intent)

        }
        flashcardsButton.setOnClickListener {
            // TODO: Start flashCards activity, using classFlashcards as the data
        }
        resourcesButton.setOnClickListener {
            // TODO: Start resources activity, using classResources as the data
            intent = Intent(this@ClassViewActivity, ResourceActivity::class.java)
            startActivity(intent)

        }
    }
}