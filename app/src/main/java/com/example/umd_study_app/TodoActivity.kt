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

class TodoActivity : ListActivity() {
    /*override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo)
    }*/

    internal lateinit var mAdapter: ToDoListAdapter

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Create a new TodoListAdapter for this ListActivity's ListView
        mAdapter = ToDoListAdapter(applicationContext)

        // Put divider between ToDoItems and FooterView
        listView.setFooterDividersEnabled(true)

        // TODO - Inflate footerView for footer_view.xml file
        var footerView: TextView? = null
        footerView = Button(this)//layoutInflater.inflate(R.layout.footer_view, null) as TextView

        // TODO - Add footerView to ListView
        this.listView.addFooterView(footerView)

        footerView.setOnClickListener {

            Log.i(TAG, "Entered footerView.OnClickListener.onClick()")

            // TODO - Attach Listener to FooterView. Implement onClick().
            val intent = Intent(applicationContext, AddTodoActivity::class.java)
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