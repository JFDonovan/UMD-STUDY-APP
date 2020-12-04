package com.e.studyapplogin

import java.io.File

data class Class (val id: String = "", val className: String = "", var notes: HashMap<String, String>, var flashcards: HashMap<String, String>, var resources: HashMap<String, File>)