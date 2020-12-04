package com.e.studyapplogin

import com.google.type.DateTime
import java.io.File

data class User (val email: String = "" , var classes: List<String>, var todo: HashMap<DateTime, HashMap<String, String>>)