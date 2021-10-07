package com.transtour.chofer.ui.activitys

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.transtour.chofer.R

class ErrorActivity():AppCompatActivity() {

    private lateinit var btnBackError: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_error)

    }

}