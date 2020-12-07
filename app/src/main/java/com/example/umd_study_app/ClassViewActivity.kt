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
    private var classNotes: HashMap<String, String>? = null
    private var classFlashcards: HashMap<String, Array<String>>? = null
    private var classResources: HashMap<String, File>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_view)

        classId = intent.extras?.get("classId") as String
        className = intent.extras?.get("className") as String
        classNotes = intent.extras?.get("classNotes") as HashMap<String, String>
        classFlashcards = intent.extras?.get("classFlashcards") as HashMap<String, Array<String>>
        classResources = intent.extras?.get("classResources") as HashMap<String, File>

        val classNameView = findViewById<TextView>(R.id.classNameView)
        val notesButton = findViewById<Button>(R.id.notesButton)
        val flashcardsButton = findViewById<Button>(R.id.flashcardsButton)
        val resourcesButton = findViewById<Button>(R.id.resourcesButton)

        classNameView.text = className
        notesButton.setOnClickListener {
            // TODO: Start notes activity, using classNotes as the data
        }
        flashcardsButton.setOnClickListener {
            // TODO: Start flashCards activity, using classFlashcards as the data
            val intent = Intent(this, FlashcardActivity::class.java).apply {
                putExtra("flashcards", classFlashcards)
                putExtra("classId", classId)
            }
            startActivity(intent)
        }
        resourcesButton.setOnClickListener {
            // TODO: Start resources activity, using classResources as the data
        }
    }
}