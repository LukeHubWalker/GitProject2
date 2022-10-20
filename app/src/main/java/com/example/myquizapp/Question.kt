package com.example.myquizapp

data class Question(
    val id: Int,
    val question: String,
    // images a stored in int
    val image: Int,
    val optionsOne:String,
    val optionsTwo:String,
    val optionsThree:String,
    val optionsFour:String,
    val correctAnswer:Int
)
