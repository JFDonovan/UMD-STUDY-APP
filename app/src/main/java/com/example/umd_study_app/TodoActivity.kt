package com.example.umd_study_app

import android.app.Activity
import android.app.ListActivity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_dashboard.*

class TodoActivity : ListActivity() {
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
    }*/
    private var todoObjects = ArrayList<ToDoItem>()
    internal lateinit var mAdapter: ToDoListAdapter
    private var userId: String? = null
    private lateinit var mThisUserDatabase : DatabaseReference


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a new TodoListAdapter for this ListActivity's ListView
        mAdapter = ToDoListAdapter(applicationContext)

        // Put divider between ToDoItems and FooterView
        listView.setFooterDividersEnabled(true)

        userId = intent.extras?.get("userId") as String?
        mThisUserDatabase = FirebaseDatabase.getInstance().getReference("Users").child(userId!!)
        var todoitem = mThisUserDatabase.child("todo")



        mThisUserDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.i(DashboardActivity.TAG, "USER DATABASE CHANGE CALLED")
                val user = dataSnapshot.getValue(User::class.java)
                var todoItems = user!!.todo
                getTodoObjects()
                //createClassButtons()
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e(DashboardActivity.TAG, "FAILED TO READ USER DATABASE")
            }
        })

         fun getTodoObjects() {
            // Clear class objects
            todoObjects.clear()

            // Render new ones
            mThisUserDatabase = FirebaseDatabase.getInstance().getReference("todo")

            for(i in ) {
                // TODO: get class objects from database based on userClasses
                /*classObjects.add(/*TODO ADD DATABASE RETRIEVAL CODE HERE ; temporary CLass ->*/ Class(
                    id = "yabadabadoo",
                    className = "Example Class",
                    notes = hashMapOf("id123" to "notes on stuffs", "id456" to "more stuffs"),
                    flashcards = hashMapOf("id123" to arrayListOf("2 + 2 = ", "4"), "id456" to arrayListOf("3 + 3 = ", "6")),
                    resources = hashMapOf("id123" to File("example.txt"))
                ))*/

                mThisUserDatabase.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        //Log.i(TAG, "CLASS DATABASE OBJECT CHANGED: ${userClasses!![i]}")
                        val todoTemp = dataSnapshot.getValue(ToDoItem::class.java)
                        if (todoTemp != null) {
                            todoObjects.add(todoTemp)
                        }
                        createTodoButtons()
                    }
                    override fun onCancelled(error: DatabaseError) {
                        Log.e(DashboardActivity.TAG, "FAILED TO READ CLASS DATABASE")
                    }
                })

            }
        }

        fun createClassButtons() {
            // Clear class buttons
            class_button_layout.removeAllViews()

            for (i in 0 until classObjects.size) {
                var classObj = classObjects[i]
                var buttonTemp = Button(this)//class_button
                buttonTemp.text = classObj.className
                buttonTemp.setOnClickListener {
                    Log.i(DashboardActivity.TAG, classObj.className)
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
                Log.i(DashboardActivity.TAG, "NEW CLASS")
                showNewClassPopup(addClassButton)
            }
            class_button_layout.addView(addClassButton)
            Log.i(DashboardActivity.TAG, "Finished Creating Class Buttons")
        }

        // TODO - Inflate footerView for footer_view.xml file
        var footerView: TextView? = null
        footerView = Button(this)//layoutInflater.inflate(R.layout.footer_view, null) as TextView

        // TODO - Add footerView to ListView
        this.listView.addFooterView(footerView)

        footerView.setOnClickListener {

            Log.i(TAG, "Entered footerView.OnClickListener.onClick()")

            // TODO - Attach Listener to FooterView. Implement onClick().
            val intent = Intent(applicationContext, AddTodoActivity::class.java).apply {
                putExtra("userId", userId)
            }
            startActivityForResult(intent, 0)
        }

        // TODO - Attach the adapter to this ListActivity's ListView
        listAdapter = mAdapter;
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        Log.i(TAG, "Entered onActivityResult()")

        // TODO - Check result code and request code
        // if user submitted a new ToDoItem
        // Create a new ToDoItem from the data Intent
        // and then add it to the adapter
        if (requestCode === 0 && resultCode === Activity.RESULT_OK) {
            val todoItem = data?.let { ToDoItem(it) }
            if (todoItem != null) {
                mAdapter.add(todoItem)
            }
        }

    }

    // Do not modify below here

    /*public override fun onResume() {
        super.onResume()

        // Load saved ToDoItems, if necessary

        if (mAdapter.count == 0)
            loadItems()
    }

    override fun onPause() {
        super.onPause()

        // Save ToDoItems

        saveItems()

    }*/

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        menu.add(Menu.NONE, MENU_DELETE, Menu.NONE, "Delete all")
        menu.add(Menu.NONE, MENU_DUMP, Menu.NONE, "Dump to log")
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            MENU_DELETE -> {
                mAdapter.clear()
                return true
            }
            MENU_DUMP -> {
                dump()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    fun dump() {
        for (i in 0 until mAdapter.count) {
            val data = (mAdapter.getItem(i) as ToDoItem).toLog()
            Log.i(TAG,
                "Item " + i + ": " + data.replace(ToDoItem.ITEM_SEP, ","))
        }
    }

    companion object {
        private val FILE_NAME = "TodoManagerActivityData.txt"
        private val TAG = "Lab-UserInterface"

        // IDs for menu items
        private val MENU_DELETE = Menu.FIRST
        private val MENU_DUMP = Menu.FIRST + 1
    }
}