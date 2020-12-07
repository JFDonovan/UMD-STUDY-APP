package com.example.umd_study_app

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

import android.content.Intent

class ToDoItem {

    var title = String()
    var priority = Priority.LOW
    var status = Status.NOTDONE
    var date = Date()

    enum class Priority {
        LOW, MED, HIGH
    }

    enum class Status {
        NOTDONE, DONE
    }

    internal constructor(title: String, priority: Priority, status: Status, date: Date) {
        this.title = title
        this.priority = priority
        this.status = status
        this.date = date
    }

    // Create a new ToDoItem from data packaged in an Intent

    internal constructor(intent: Intent) {

        title = intent.getStringExtra(ToDoItem.TITLE).toString()
        priority = intent.getStringExtra(ToDoItem.PRIORITY)?.let { Priority.valueOf(it) }!!
        status = Status.valueOf(intent.getStringExtra(ToDoItem.STATUS)!!)

        try {
            date = ToDoItem.FORMAT.parse(intent.getStringExtra(ToDoItem.DATE))
        } catch (e: ParseException) {
            date = Date()
        }

    }

    override fun toString(): String {
        return (title + ITEM_SEP + priority + ITEM_SEP + status + ITEM_SEP
                + FORMAT.format(date))
    }

    fun toLog(): String {
        return ("Title:" + title + ITEM_SEP + "Priority:" + priority
                + ITEM_SEP + "Status:" + status + ITEM_SEP + "Date:"
                + FORMAT.format(date) + "\n")
    }

    companion object {

        val ITEM_SEP = System.getProperty("line.separator")

        val TITLE = "title"
        val PRIORITY = "priority"
        val STATUS = "status"
        val DATE = "date"
        val FILENAME = "filename"

        val FORMAT = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US)

        // Take a set of String data values and
        // package them for transport in an Intent

        fun packageIntent(intent: Intent, title: String,
                          priority: Priority, status: Status, date: String) {

            intent.putExtra(ToDoItem.TITLE, title)
            intent.putExtra(ToDoItem.PRIORITY, priority.toString())
            intent.putExtra(ToDoItem.STATUS, status.toString())
            intent.putExtra(ToDoItem.DATE, date)

        }
    }

}