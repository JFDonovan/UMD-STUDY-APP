package com.example.umd_study_app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import org.w3c.dom.Text
import java.io.File

class ClassViewActivity : AppCompatActivity() {
    // Class Fields
    private var classId: String = ""
    private var className: String = ""
    private var classNotes: HashMap<String, String>? = null
    private var classFlashcards: HashMap<String, ArrayList<String>>? = null
    private var classResources: HashMap<String, File>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_view)

        // Set class fields from last intent (redundant with listener)
        classId = intent.extras?.get("classId") as String
        className = intent.extras?.get("className") as String
        classNotes = intent.extras?.get("classNotes") as HashMap<String, String>
        classFlashcards = intent.extras?.get("classFlashcards") as HashMap<String, ArrayList<String>>
        classResources = intent.extras?.get("classResources") as HashMap<String, File>

        // Add Database listeners for updates
        FirebaseDatabase.getInstance().getReference("Classes").child(classId).addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i(DashboardActivity.TAG, "CLASS DATABASE OBJECT CHANGED: $classId")
                val classTemp = dataSnapshot.getValue(Class::class.java)
                if (classTemp != null) {
                    className = classTemp.className
                    classNotes = classTemp.notes
                    classFlashcards = classTemp.flashcards
                    classResources = classTemp.resources
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e(DashboardActivity.TAG, "FAILED TO READ CLASS DATABASE")
            }
        })

        // Get Views
        val classNameView = findViewById<TextView>(R.id.classNameView)
        val notesButton = findViewById<Button>(R.id.notesButton)
        val flashcardsButton = findViewById<Button>(R.id.flashcardsButton)
        val classCodeView = findViewById<TextView>(R.id.classCodeView)

        // Set class name and id fields (id is selectable)
        classNameView.text = className
        classCodeView.text = classId

        // On-click listeners for class menu options
        notesButton.setOnClickListener {
            // TODO: Start notes activity, using classNotes as the data
            intent = Intent(this@ClassViewActivity, NotesActivity::class.java)
            intent.putExtra("className", className)
            intent.putExtra("classId", classId)
            intent.putExtra("classNotes", classNotes)
            startActivity(intent)
        }
        flashcardsButton.setOnClickListener {
            // TODO: Start flashCards activity, using classFlashcards as the data
            val intent = Intent(this, FlashcardActivity::class.java).apply {
                putExtra("flashcards", classFlashcards)
                putExtra("classId", classId)
            }
            startActivity(intent)
        }

    }
}