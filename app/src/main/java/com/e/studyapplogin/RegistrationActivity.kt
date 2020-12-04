package com.e.studyapplogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
<<<<<<< HEAD
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase
=======
>>>>>>> 392512b6474b0a9e1a4513913193cf76670d25da

class RegistrationActivity : AppCompatActivity() {
    private var emailTV: EditText? = null
    private var passwordTV: EditText? = null
    private var regBtn: Button? = null
    private var progressBar: ProgressBar? = null
    private var validator = Validators()
<<<<<<< HEAD
    private lateinit var database: DatabaseReference
=======
>>>>>>> 392512b6474b0a9e1a4513913193cf76670d25da

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

                    val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {

                    Toast.makeText(applicationContext, "Registration failed! Please try again later", Toast.LENGTH_LONG).show()
                    progressBar!!.visibility = View.GONE

                }
            }

<<<<<<< HEAD
        database = Firebase.database.reference

     //    fun writeNewUser(userId: String, email: String?) {
    //        val user = User(email)
   //         database.child("users").child(userId).setValue(user)
    //    }


=======
>>>>>>> 392512b6474b0a9e1a4513913193cf76670d25da

    }
    companion object {
        const val TAG = "umdstudyapp"
    }
}