package com.example.umd_study_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import java.io.File

val DATABASE_NAME = "classDB"
val TABLE_NAME = "CLASSES"
val COL_ID = "ID"
val COL_NAME = "NAME"
//val COL_NOTES = "Notes"
val COL_FLASHCARDS = "FLASHCARDS"
val COL_RESOURCES = "RESOURCES"

class DataBaseHandler(var context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {

        val createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,NAME VARCHAR(256), FLASHCARDS TEXT, RESOURCES TEXT)"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(classObj : Class) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put(COL_NAME, classObj.className)
        cv.put(COL_FLASHCARDS, classObj.flashcards.toString())
        cv.put(COL_RESOURCES, classObj.resources.toString())
        var result = db.insert(TABLE_NAME, null, cv)

        if (result == -1.toLong()){
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show()
        }
    }

    fun readData() : MutableList<ArrayList<String>> {
        var list : MutableList<ArrayList<String>> = ArrayList()

        val db = this.readableDatabase
        val query = "Select * from " + TABLE_NAME
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()) {
            do{
                var classObj = Class()
                var lst : ArrayList<String> = ArrayList()
                //classObj.id = result.getString(0).toInt()
                //classObj.className = result.getString(1)
                //classObj.flashcards = result.getString(2) as HashMap<String, HashMap<String, String>>
                //classObj.resources = result.getString(3) as HashMap<String, File>
                lst.add(result.getString(0))
                lst.add(result.getString(1))
                lst.add(result.getString(2))
                lst.add(result.getString(3))
                list.add(lst)

            }while (result.moveToNext())
        }
        return list
    }
}