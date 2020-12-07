package com.example.umd_study_app

import java.io.File

data class Class (val id: String = "", val className: String = "", var notes: HashMap<String, String>, var flashcards: HashMap<String, ArrayList<String>>, var resources: HashMap<String, File>) {
    constructor() : this("", "", hashMapOf(), hashMapOf(), hashMapOf())
}