package com.example.umd_study_app

import java.util.*
import kotlin.collections.HashMap

//data class User (val email: String = "" , var classes: Array<String>, var todo: HashMap<Date, HashMap<String, String>>)


class User {
    var id : Int = 0
    var email : String = ""
    // var notes: HashMap<String, String> = HashMap<String, String>()
    var classList: String = ""
    //var flashcards: HashMap<String, Array<String>> = HashMap<String, Array<String>>()
    var todo: String = ""

    constructor(email : String, classList:String, todo: String) {
        this.email = email
        // this.notes = n
        this.classList = classList
        this.todo = todo
    }

    constructor() {
    }
}