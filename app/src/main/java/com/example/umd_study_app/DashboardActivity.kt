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
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_dashboard.*
import java.io.File
import java.util.*
import kotlin.collections.HashMap


class DashboardActivity : AppCompatActivity() {
    // Given directly with User object
    private var userId: String? = null
    private var userClasses: Array<String> = arrayOf()
    private var todoItems: HashMap<Date, HashMap<String, String>>? = null

    // Must be put together from database
    private var classObjects: ArrayList<Class> = arrayListOf()

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
        userId = "qwerqwer12341234" // TEMP
        userClasses = arrayOf("TEMP", "TEMP", "TEMP")//, "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP", "TEMP") // TEMP
        todoItems = hashMapOf(Date() to hashMapOf("task" to "Eat ur homework", "category" to "Example Class")) // TEMP



        var teststringarray = Array (100){ "test" }
        teststringarray[1] = "test2"
        var testflashcard = HashMap<String, Array<String>>()
        testflashcard.put("TestKey", teststringarray)
        var gson = Gson()
        var teststr = gson.toJson(testflashcard)


        for(i in userClasses.indices) {
            // TODO: get class objects from database based on userClasses
            classObjects.add(/*TODO ADD DATABASE RETRIEVAL CODE HERE ; temporary CLass ->*/ Class(
                className = "Example Class",
                //notes = hashMapOf("id123" to "notes on stuffs", "id456" to "more stuffs"),
                flashcards = teststr,
                resources = ""
            ))
        }
        for (i in 0 until classObjects.size) {
            var classObj = classObjects[i]
            var buttonTemp = Button(this)//class_button
            buttonTemp.text = classObj.className
            buttonTemp.setOnClickListener {
                Log.i(TAG, classObj.className)
                val intent = Intent(this, ClassViewActivity::class.java).apply {
                    putExtra("className", classObj.className)
                    //putExtra("classNotes", classObj.notes)
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
                }
                startActivity(intent)
            }
            if (it.title == "Create Class") {
                val intent = Intent(this, CreateClassActivity::class.java).apply {
                    putExtra("userId", userId)
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