package com.example.myquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonStart: Button = findViewById(R.id.button)
        val startTest: TextView = findViewById(R.id.et_name)
        buttonStart.setOnClickListener {

            if(startTest.text.isEmpty())
            {
                    Toast.makeText(this, "Enter a Name", Toast.LENGTH_LONG).show()
            }else{
                val intent = Intent(this,QuizQuestionsActivity::class.java)

                intent.putExtra(Constants.USER_NAME,startTest.text.toString())

                //starts the main activity
                startActivity(intent)
                //closed the main activity
                finish()
            }
        }
    }
}