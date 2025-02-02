package com.example.umd_study_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {
    private var emailTV: EditText? = null
    private var passwordTV: EditText? = null
    private var regBtn: Button? = null
    private var progressBar: ProgressBar? = null
    private var validator = Validators()

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        mAuth = FirebaseAuth.getInstance()

        emailTV = findViewById(R.id.email)
        passwordTV = findViewById(R.id.password)
        regBtn = findViewById(R.id.register)
        progressBar = findViewById(R.id.progressBar)

        regBtn!!.setOnClickListener { registerNewUser() }
    }

    private fun registerNewUser() {
        progressBar!!.visibility = View.VISIBLE

        val email: String = emailTV!!.text.toString()
        val password: String = passwordTV!!.text.toString()

        if (!validator.validEmail(email)) {
            Toast.makeText(applicationContext, "Please enter a valid email...", Toast.LENGTH_LONG).show()
            return
        }
        if (!validator.validPassword(password)) {
            Toast.makeText(applicationContext, "Please enter a valid password!", Toast.LENGTH_LONG).show()
            return
        }

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {  task ->
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Registration successful!", Toast.LENGTH_LONG).show()
                    progressBar!!.visibility = View.GONE

                    var userObj = User(email, arrayListOf(), hashMapOf())
                    var userId = mAuth.uid
                    var database = FirebaseDatabase.getInstance()
                    var myRef = database.getReference("Users")

                    if (userId != null) {
                        Log.i(TAG, "Write to Database: $myRef User id: $userId")
                        myRef.child(userId).setValue(userObj)
                    }
                    else {
                        Log.e(TAG, "USER ID IS NULL")
                    }

                    val intent = Intent(this@RegistrationActivity, MainActivity::class.java)
                    startActivity(intent)
                } else {

                    Toast.makeText(applicationContext, "Registration failed! Please try again later", Toast.LENGTH_LONG).show()
                    progressBar!!.visibility = View.GONE

                }
            }
    }
    companion object {
        const val TAG = "umdstudyapp"
    }
}