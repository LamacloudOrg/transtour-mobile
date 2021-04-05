package com.transtour.chofer.ui.activitys

import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.transtour.chofer.R

class TravelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel)

        val intent:Intent = intent
        val user: String? = intent.getStringExtra("userName");

        val textViewChofer:TextView = findViewById(R.id.textViewChofer)
        textViewChofer.text = user
    }

    fun signDocument(view: View) {

        val path = Environment.getExternalStorageState()
        val fileName ="Payment Voucher Template PDF.pdf"
        val id_file  = 1

        val intent = getPackageManager().getLaunchIntentForPackage("com.xodo.pdf.reader");
        if (intent != null) {
               intent.apply {
                   addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                   addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                   putExtra(DocumentsContract.EXTRA_INITIAL_URI,path+"/"+fileName)

               }
             startActivity(intent)
        }
    }


}