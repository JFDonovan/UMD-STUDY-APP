package com.example.umd_study_app

import java.util.*
import kotlin.collections.HashMap

data class User (val email: String = "" , var classes: Array<String>, var todo: HashMap<Date, HashMap<String, String>>)