package com.example.umd_study_app

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*


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
        mDatabaseReference!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                println(p0!!.message)

            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val upload = postSnapshot.getValue(Upload::class.java)!!
                    (uploadList as ArrayList<Upload>).add(upload)
                }

                val uploads = arrayOfNulls<String>(uploadList.size)

                for (i in uploads.indices) {
                    uploads[i] = uploadList[i].name
                }

                val adapter = ArrayAdapter<String>(
                    applicationContext, android.R.layout.simple_list_item_1, uploads
                )
                listView!!.adapter = adapter
            }
        })







    }



    companion object{
        val STORAGE_PATH_UPLOADS = "uploads/"
        val DATABASE_PATH_UPLOADS = "uploads"
        val PDF_CODE = 2342

    }



}