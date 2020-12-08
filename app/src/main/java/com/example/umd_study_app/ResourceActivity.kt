package com.example.umd_study_app

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_resource.*


class ResourceActivity : AppCompatActivity(){

    //these are the views
    lateinit var ViewStatus : TextView
    var editTextFilename: EditText? = null
    var progressBar: ProgressBar? = null

    //the firebase objects for storage and database
    var mStorageReference: StorageReference? = null
    var mDatabaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resource)
        val dbURL : String = "https://umd-study-app-default-rtdb.firebaseio.com/"
        val storageURL: String = "gs://umd-study-app.appspot.com"
        mStorageReference = FirebaseStorage.getInstance().reference
        mDatabaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(dbURL)

        ViewStatus = findViewById<TextView>(R.id.ViewStatus_text)
        editTextFilename = findViewById<EditText>(R.id.editTextFileName)
        progressBar = findViewById<ProgressBar>(R.id.progressbar)



        val uploadButton = findViewById<Button>(R.id.uploadButton)
        val viewUploads = findViewById<TextView>(R.id.ViewUploads_text)
        //val viewResource = findViewById<Button>(R.id.viewResources)

        uploadButton.setOnClickListener{
           //TODO: Add functionality for the upload Button
            getPDF()
        }
        viewUploads.setOnClickListener {
            //GoToDangerousActivity::class.java
            val intent = Intent(this, ViewFiles::class.java)
            startActivity(intent)

        }
        // TODO: Add a listener for the viewUploads editText thing
    }
    //gets the PDF file that was uploaded
    private fun getPDF(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            val intent = Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                Uri.parse("package:".plus(" ").plus(packageName))
            )
            startActivity(intent)
            return
        }

        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PDF_CODE)

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PDF_CODE && resultCode == RESULT_OK && data != null && data.data != null) {
            //if a file is selected
            if (data.data != null) {
                //uploading the file
                uploadFile(data.data!!)
            }else{
                Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private fun uploadFile(data: Uri){
        progressBar!!.visibility = View.VISIBLE
        val pathStr : String = STORAGE_PATH_UPLOADS + System.currentTimeMillis().toString() + ".pdf"
        val sRef = mStorageReference!!.child(pathStr)
        sRef.putFile(data).addOnSuccessListener { taskSnapshot ->
            progressBar!!.visibility = View.GONE
            ViewStatus.text = "File Uploaded Successfully"


            val dlURL = taskSnapshot.storage.downloadUrl.toString()

            val upload = Upload(
                editTextFilename!!.text.toString(), dlURL
            )
            mDatabaseReference!!.child(mDatabaseReference!!.push().key!!).setValue(upload)

        }.addOnFailureListener {
            exception ->
                Toast.makeText(applicationContext, exception.message, Toast.LENGTH_LONG).show()

        }.addOnProgressListener {
            taskSnapshot->
                val progress: Double =
                    100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                val str: String = progress.toString().plus(" ").plus("% Uploading...")
                ViewStatus.text = str

        }


    }

    companion object{
        val STORAGE_PATH_UPLOADS = "uploads/"
        val DATABASE_PATH_UPLOADS = "uploads"
        val PDF_CODE = 2342


    }

}