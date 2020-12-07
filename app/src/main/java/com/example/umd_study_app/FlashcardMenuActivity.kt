package com.example.umd_study_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class FlashcardMenuActivity : AppCompatActivity() {

    private var mFlashcards: HashMap<String, Array<String>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flashcard_menu)

        mFlashcards = intent.extras?.get("flashcards") as HashMap<String, Array<String>>
    }
}