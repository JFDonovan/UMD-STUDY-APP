package com.example.umd_study_app

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class DashboardActivity : AppCompatActivity() {
    // Given directly with User object
    private var userId: String? = null
    private var userClasses: ArrayList<String> = arrayListOf()
    private var todoItems: HashMap<Int, HashMap<String, String>>? = null

    // Must be put together from database
    private var classObjects: HashMap<String, Class> = hashMapOf()

    // Database fields
    private lateinit var mThisUserDatabase : DatabaseReference
    private lateinit var mClassDatabase : DatabaseReference

    // Customize top bar menu
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return true
    }

    // Set top bar menu functionality
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i(TAG, "${item.title}")
        if (item.title == "Add Class") {
            showNewClassPopup(findViewById(R.id.action_add_class))
        }
        if (item.title == "TODO List") {
            val intent = Intent(this, TodoActivity::class.java)
            startActivity(intent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Must initialize
        userClasses = arrayListOf()
        todoItems = hashMapOf()

        // Set user ID from extra
        userId = intent.extras?.get("userId") as String

        // Current user database reference
        mThisUserDatabase = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)

        // Read user data from database and add listener for change
        mThisUserDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i(TAG, "USER DATABASE CHANGE CALLED")
                val user = dataSnapshot.getValue(User::class.java)
                userClasses = user!!.classes
                todoItems = user!!.todo
                getClassObjects()
                //createClassButtons()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "FAILED TO READ USER DATABASE")
            }
        })
    }

    private fun getClassObjects() {
        Log.i(TAG, "GET CLASS OBJECTS CALLED")
        // Clear class objects
        classObjects.clear()
        // Render new ones

        // Class database reference
        mClassDatabase = FirebaseDatabase.getInstance().getReference("Classes")

        // For each class user is enrolled in, get class object
        for(i in userClasses.indices) {
            Log.e(TAG, "$i")
            mClassDatabase.child(userClasses[i]).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.i(TAG, "CLASS DATABASE OBJECT CHANGED: ${userClasses[i]}")
                    val classTemp = dataSnapshot.getValue(Class::class.java)
                    if (classTemp != null) {
                        //classObjects.add(classTemp)
                        classObjects.set(userClasses[i], classTemp)
                    }
                    createClassButtons()
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "FAILED TO READ CLASS DATABASE")
                }
            })

        }
        createClassButtons()
    }

    private fun createClassButtons() {
        // Clear class buttons
        class_button_layout.removeAllViews()

        var classObjectsIndices = classObjects.keys

        // For all class objects create previously, create buttons and assigned click listeners
        for (i in 0 until classObjectsIndices.size) {
            var classObj = classObjects[classObjectsIndices.elementAt(i)]
            var buttonTemp = Button(this)//class_button
            buttonTemp.text = classObj!!.className
            buttonTemp.setOnClickListener {
                Log.i(TAG, "Select Class ${classObj!!.className}")
                val intent = Intent(this, ClassViewActivity::class.java).apply {
                    putExtra("classId", classObj.id)
                    putExtra("className", classObj.className)
                    putExtra("classNotes", classObj.notes)
                    putExtra("classFlashcards", classObj.flashcards)
                    putExtra("classResources", classObj.resources)
                }
                startActivity(intent)
            }
            // On long click delete dialog appears
            buttonTemp.setOnLongClickListener {
                Log.i(TAG, "Delete Class ${classObj!!.className}")
                val dialogBuilder = AlertDialog.Builder(this)
                dialogBuilder.setCancelable(true)
                dialogBuilder.setTitle("Remove Class?")
                dialogBuilder.setMessage("Are you sure you want to remove ${classObj!!.className}")
                dialogBuilder.setPositiveButton("Confirm") { _, _ ->
                    Log.e(TAG, "Confirmed Delete")
                    userClasses.remove(classObj.id)
                    var userRef = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)
                    userRef.child("classes").setValue(userClasses)
                }
                dialogBuilder.setNegativeButton("Cancel") { _, _ ->
                    Log.e(TAG, "Canceled Delete")
                }

                dialogBuilder.show()

                return@setOnLongClickListener true
            }
            class_button_layout.addView(buttonTemp)
        }
        var addClassButton = Button(this)
        addClassButton.text = "+"
        addClassButton.setOnClickListener {
            Log.i(TAG, "NEW CLASS")
            showNewClassPopup(addClassButton)
        }
        class_button_layout.addView(addClassButton)
        Log.i(TAG, "Finished Creating Class Buttons: ${classObjects.size}")
    }

    // Popup menu for selecting whether to create or join a class
    private fun showNewClassPopup(view: View) {
        var popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.add_class_popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            Log.i(TAG, "$it")
            if (it.title == "Join Class") {
                val intent = Intent(this, JoinClassActivity::class.java).apply {
                    putExtra("userId", userId)
                    putExtra("userClassList", userClasses)
                }
                startActivity(intent)
            }
            if (it.title == "Create Class") {
                val intent = Intent(this, CreateClassActivity::class.java).apply {
                    putExtra("userId", userId)
                    putExtra("userClassList", userClasses)
                }
                startActivity(intent)
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    companion object {
        const val TAG = "UMD-STUDY-APP"
    }
}