package com.example.umd_study_app

import java.util.*
import kotlin.collections.HashMap

data class User (val email: String = "" , var classes: ArrayList<String>, var todo: HashMap<Int, HashMap<String, String>>) {
    constructor() : this("", arrayListOf(), hashMapOf())
}