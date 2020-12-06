package com.example.umd_study_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


//import java.util.ArrayList
//import java.util.List

class ViewFiles : AppCompatActivity(){

    var listView: ListView? = null

    //database reference to get uploads data
    var mDatabaseReference: DatabaseReference? = null

    //list to store uploads data
    lateinit var uploadList: List<Upload>

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_files)
        uploadList = arrayListOf()
        listView = findViewById<ListView>(R.id.listView) as ListView

        listView!!.setOnItemClickListener{ parent, view, position, id ->
            val upload = uploadList[position]
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(upload.url)
            startActivity(intent)

        }

        mDatabaseReference = FirebaseDatabase.getInstance().getReference(DATABASE_PATH_UPLOADS)
        mDatabaseReference!!.addValueEventListener







    }



    companion object{
        val STORAGE_PATH_UPLOADS = "uploads/"
        val DATABASE_PATH_UPLOADS = "uploads"
        val PDF_CODE = 2342

    }



}