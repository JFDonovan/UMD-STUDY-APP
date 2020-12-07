package com.example.umd_study_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class AddFlashcardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_flashcard)

        val questionView = findViewById<EditText>(R.id.newFlashcardQuestion)
        val answerView = findViewById<EditText>(R.id.newFlashcardAnswer)
        val submitButton = findViewById<Button>(R.id.createFlashcardSubmitButton)

        submitButton.setOnClickListener {
            val classId = intent.getStringExtra("classId")
            var question = questionView.text.toString()
            var answer = answerView.text.toString()
            if (question.isNotEmpty() && answer.isNotEmpty()) {
                Log.i(CreateClassActivity.TAG, "Create Flashcard: $question ||| $answer \nClass ID: $classId")
                // TODO: send new flashcard to database with "question" as question and answer as answer NOTE: make
                //  sure that adding to database causes classview to update (timing may get weird causing
                //  need to wait for response that writing was successful)

                var flashcardId = UUID.randomUUID().toString()
                var flashcardObj = arrayListOf<String>(question, answer)
                var classFlashcardsRef = FirebaseDatabase.getInstance().getReference("Classes").child(classId!!).child("flashcards")

                classFlashcardsRef.child(flashcardId).setValue(flashcardObj)

                finish()
            }
            else {
                val toast = Toast.makeText(this, "One or more blank fields", Toast.LENGTH_SHORT)
                toast.show()
            }
        }


    }

    companion object {
        const val TAG = "UMD-STUDY-APP"
    }
}