package com.example.umd_study_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.gson.Gson
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
import java.io.File

class CreateClassActivity : AppCompatActivity() {

    private lateinit var databaseClasses: DatabaseReference
    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_class)

        val createClassNameView = findViewById<EditText>(R.id.createClassNameView)
        val createClassSubmitButton = findViewById<Button>(R.id.createClassSubmitButton)
        var userId = intent.getStringExtra("userId")
        mAuth = FirebaseAuth.getInstance()
        var email = mAuth.currentUser!!.email as String


        //var mDatabase: FirebaseDatabase = FirebaseDatabase.getInstance()
        //var mDatabaseReference: DatabaseReference = mDatabase.reference
        databaseClasses = FirebaseDatabase.getInstance().getReference("/")

       // val database = Firebase.database
    //    val myRef = database.getReference("message")

   //     myRef.setValue("Hello, World!")
        val context = this


        createClassSubmitButton.setOnClickListener {
            var name = createClassNameView.text
            Log.i(TAG, "Create Class: $name")
            // TODO: send new class to database with "name" as name and userId as user id NOTE: make sure that adding to database causes classview to update (timing may get weird causing need to wait for response that writing was successful)
            var notes : HashMap<String, String> = HashMap<String, String>()
            var flashcards : HashMap<String, Array<String>> = HashMap<String, Array<String>>()
            var resources : HashMap<String, File> = HashMap<String, File>()

            //var teststringarray = emptyArray<String>()

            //var testflashcard = HashMap<String, Array<String>>()
            //var teststr = gson.toJson(testflashcard)
            //Toast.makeText(context, teststr, Toast.LENGTH_LONG).show()

            //flashcards.put("TestKey", emptyArray<String>())


            var gson = Gson()
            var flashstring = gson.toJson(flashcards) as String

            var resstring = gson.toJson(resources) as String

            var mClass : Class = Class(name.toString(), flashstring, resstring)
            var mUser : User = User(email, resstring, resstring)

            var db = DataBaseHandler(context)

            //db.deleteDataBase()
            var res = db.insertClass(mClass)

            if (res == -1.toLong()){
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
            }

            var data = db.readClass()
            Toast.makeText(context, data.toString(), Toast.LENGTH_LONG).show()

           // var test = ""
           // for (i  in 0..(data.size - 1)){
           //     test = test + data[i].toString()
           //     Log.i(TAG, "TESTING " + test.toString())
            //}


            finish()
        }
    }

    companion object {
        const val TAG = "UMD-STUDY-APP"
    }
}