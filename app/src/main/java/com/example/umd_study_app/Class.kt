package com.example.umd_study_app

import java.io.File

class Class {
    var id : Int = 0
    var className : String = ""
   // var notes: HashMap<String, String> = HashMap<String, String>()
    var flashcards: String = ""
    //var flashcards: HashMap<String, Array<String>> = HashMap<String, Array<String>>()
    var resources: String = ""

    constructor(className : String, flashcards:String, resources: String) {
        this.className = className
       // this.notes = notes
        this.flashcards = flashcards
        this.resources = resources
    }

    constructor() {
    }
}

//data class Class (val className: String = "", var notes: HashMap<String, String>, var flashcards: HashMap<String, HashMap<String, String>>, var resources: HashMap<String, File>)