package com.e.studyapplogin

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
<<<<<<< HEAD
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


public class LoginActivity : AppCompatActivity() {
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
=======


public class LoginActivity : AppCompatActivity() {
    //private var mDatabaseReference: DatabaseReference? = null
    //private var mDatabase: FirebaseDatabase? = null
>>>>>>> 392512b6474b0a9e1a4513913193cf76670d25da
    private var userEmail: EditText? = null
    private var userPassword: EditText? = null
    private var loginBtn: Button? = null
    private var progressBar: ProgressBar? = null

    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

<<<<<<< HEAD
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
=======
        //mDatabase = FirebaseDatabase.getInstance()
        //mDatabaseReference = mDatabase!!.reference.child("Users")
>>>>>>> 392512b6474b0a9e1a4513913193cf76670d25da
        mAuth = FirebaseAuth.getInstance()

        userEmail = findViewById(R.id.email)
        userPassword = findViewById(R.id.password)
        loginBtn = findViewById(R.id.login)
        progressBar = findViewById(R.id.progressBar)

        loginBtn!!.setOnClickListener { loginUserAccount() }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = mAuth!!.currentUser

    }

    // TODO: Allow the user to log into their account
    // If the email and password are not empty, try to log in
    // If the login is successful, store info into intent and launch DashboardActivity
    private fun loginUserAccount() {
        progressBar!!.visibility = View.VISIBLE

        val email: String = userEmail!!.text.toString()
        val password: String = userPassword!!.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Please enter email...", Toast.LENGTH_LONG).show()
            return
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Please enter password!", Toast.LENGTH_LONG).show()
            return
        }

        mAuth!!.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                progressBar!!.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Successful Login", Toast.LENGTH_LONG).show()

                    startActivity(Intent(this@LoginActivity, DashboardActivity::class.java).putExtra(
                        USER_ID, mAuth!!.uid))
                } else {
                    Toast.makeText(applicationContext, "Login failed! Please try again later", Toast.LENGTH_LONG).show()
                }
            }
    }

    companion object {
        const val USER_EMAIL = "com.example.tesla.myhomelibrary.useremail"
        const val USER_ID = "com.example.tesla.myhomelibrary.userid"
    }

}
