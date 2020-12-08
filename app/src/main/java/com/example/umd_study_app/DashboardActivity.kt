package com.example.umd_study_app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.PopupMenu
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
    private var classObjects: ArrayList<Class> = arrayListOf()

    // Database stuff
    private lateinit var mThisUserDatabase : DatabaseReference
    private lateinit var mClassDatabase : DatabaseReference

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return true
    }

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
        // TODO: get user object (either as extra from when intent is created or from database now) and set the following fields
        userClasses = arrayListOf("TEMP", "TEMP", "TEMP")//, "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP") // TEMP
        todoItems = hashMapOf(100 to hashMapOf("task" to "Eat ur homework", "category" to "Example Class")) // TEMP
        userId = intent.extras?.get("userId") as? String
        mThisUserDatabase = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)

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

    override fun onResume() {
        super.onResume()
        getClassObjects()
    }

    private fun getClassObjects() {
        // Clear class objects
        classObjects.clear()

        // Render new ones
        mClassDatabase = FirebaseDatabase.getInstance().getReference("Classes")

        for(i in userClasses.indices) {
            // TODO: get class objects from database based on userClasses
            /*classObjects.add(/*TODO ADD DATABASE RETRIEVAL CODE HERE ; temporary CLass ->*/ Class(
                id = "yabadabadoo",
                className = "Example Class",
                notes = hashMapOf("id123" to "notes on stuffs", "id456" to "more stuffs"),
                flashcards = hashMapOf("id123" to arrayListOf("2 + 2 = ", "4"), "id456" to arrayListOf("3 + 3 = ", "6")),
                resources = hashMapOf("id123" to File("example.txt"))
            ))*/

            mClassDatabase.child(userClasses[i]).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    Log.i(TAG, "CLASS DATABASE OBJECT CHANGED: ${userClasses[i]}")
                    val classTemp = dataSnapshot.getValue(Class::class.java)
                    if (classTemp != null) {
                        classObjects.add(classTemp)
                    }
                    createClassButtons()
                }
                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "FAILED TO READ CLASS DATABASE")
                }
            })

        }
    }

    private fun createClassButtons() {
        // Clear class buttons
        class_button_layout.removeAllViews()

        for (i in 0 until classObjects.size) {
            var classObj = classObjects[i]
            var buttonTemp = Button(this)//class_button
            buttonTemp.text = classObj.className
            buttonTemp.setOnClickListener {
                Log.i(TAG, classObj.className)
                val intent = Intent(this, ClassViewActivity::class.java).apply {
                    putExtra("classId", classObj.id)
                    putExtra("className", classObj.className)
                    putExtra("classNotes", classObj.notes)
                    putExtra("classFlashcards", classObj.flashcards)
                    putExtra("classResources", classObj.resources)
                }
                startActivity(intent)
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
        Log.i(TAG, "Finished Creating Class Buttons")
    }

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