package com.transtour.chofer.activitys

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.transtour.chofer.R

class TravelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel)

        Intent intent = getIntent()
        val user:String = intent.getStringExtra("userName");

        val textViewChofer:TextView = findViewById(R.id.textViewChofer)
        textViewChofer.text = user
    }
}