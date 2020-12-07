package com.example.umd_study_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.database.ktx.database
//import com.google.firebase.ktx.Firebase
import java.io.File

class CreateClassActivity : AppCompatActivity() {

    private lateinit var databaseClasses: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_class)

        val createClassNameView = findViewById<EditText>(R.id.createClassNameView)
        val createClassSubmitButton = findViewById<Button>(R.id.createClassSubmitButton)
        var userId = intent.getStringExtra("userId")


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
            var flashcards : HashMap<String, HashMap<String, String>> = HashMap<String, HashMap<String,String>>()
            var resources : HashMap<String, File> = HashMap<String, File>()



            var mClass : Class = Class(name.toString(), flashcards, resources)
            var db = DataBaseHandler(context)
            db.insertData(mClass)
            //mDatabaseReference.setValue("Test Connection")
            //databaseClasses.push()
            //val id = databaseClasses.child("classes").push()
            //databaseClasses.child("classes").setValue(mClass)
            //databaseClasses.child("classes").push()

            //val firebaseAuth = FirebaseDatabase.getInstance().getReference("/").child("classes")
            //val dbref = firebaseAuth.toString()
            //Log.i(TAG, "DBREF: $dbref")
            //databaseClasses.push()
           // mDatabaseReference.child("class").child(name.toString()).setValue(mClass)
            //Log.i(TAG, "Class Created: $result2")
            var data = db.readData()
            var test = ""
            for (i in 0..(data.size-1)){
                test = test + (data.get(i)[0] + " " + data.get(i)[1] + " " + data.get(i)[2] + " " + data.get(i)[3] )
            }
            Log.i(TAG, "TESTING" + test)
            finish()
        }
    }

    companion object {
        const val TAG = "UMD-STUDY-APP"
    }
}