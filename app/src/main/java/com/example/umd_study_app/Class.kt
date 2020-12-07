package com.example.umd_study_app

import java.io.File

class Class {
    var id : Int = 0
    var className : String = ""
   // var notes: HashMap<String, String> = HashMap<String, String>()
    var flashcards: HashMap<String, HashMap<String, String>> = HashMap<String, HashMap<String, String>>()
    var resources: HashMap<String, File> = HashMap<String, File>()

    constructor(className : String, flashcards: HashMap<String, HashMap<String, String>>, resources: HashMap<String, File>) {
        this.className = className
       // this.notes = notes
        this.flashcards = flashcards
        this.resources = resources
    }

    constructor() {
    }
}

//data class Class (val className: String = "", var notes: HashMap<String, String>, var flashcards: HashMap<String, HashMap<String, String>>, var resources: HashMap<String, File>)