package com.transtour.chofer.ui.activitys

import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.transtour.chofer.R
import com.transtour.chofer.model.Travel
import com.transtour.chofer.service.TravelInfoService
import com.transtour.chofer.viewmodel.TravelViewModel
import java.util.*


class TravelActivity : AppCompatActivity() {

    val TAG: String = "TravelActivity"
    lateinit var travelViewModel: TravelViewModel
    lateinit var tvViajeHora: TextView
    lateinit var tvViajeFecha: TextView
    lateinit var tvPasajeroDocumento: TextView
    lateinit var tvPasajeroTelefono :TextView
    lateinit var tvPasajeroNombre:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel)

        val intent:Intent = intent
        val user: String? = intent.getStringExtra("userName");

        val textViewChofer:TextView = findViewById(R.id.tvChoferNombre)
        textViewChofer.text = user

        configView()
    }


    fun  configView(){

        travelViewModel = TravelViewModel()
        tvViajeHora = findViewById(R.id.tvViajeHora)
        tvViajeFecha = findViewById(R.id.tvViajeFecha)
        tvPasajeroDocumento = findViewById(R.id.tvPasajeroDocumento)
        tvPasajeroTelefono = findViewById(R.id.tvPasajeroTelefono)
        tvPasajeroNombre = findViewById(R.id.tvPasajeroNombre)


        val  observer = Observer<Travel>(){
            travel -> display(travel)
        }
        travelViewModel!!.resultado.observe(this,observer)
        }

    fun display(travel:Travel){
        tvViajeHora.text = travel.hour
        tvViajeFecha.text = travel.date
        tvPasajeroDocumento.text = travel.passanger?.document
        tvPasajeroTelefono.text = travel.passanger?.document
        tvPasajeroNombre.text = travel.passanger?.document

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
                   putExtra(DocumentsContract.EXTRA_INITIAL_URI, path + "/" + fileName)

               }
             startActivity(intent)
        }
    }

    @SuppressLint("NewApi")
    fun scheduleJob(v: View?) {
        val componentName = ComponentName(this, TravelInfoService::class.java)
        val info = JobInfo.Builder(123, componentName)
                .setRequiresCharging(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPersisted(true)
                .setPeriodic((15 * 60 * 1000).toLong())
                .build()
        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        val resultCode = scheduler.schedule(info)
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled")
        } else {
            Log.d(TAG, "Job scheduling failed")
        }
    }

    @SuppressLint("NewApi")
    fun cancelJob(v: View?) {
        val scheduler = getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler
        scheduler.cancel(123)
        Log.d(TAG, "Job cancelled")
    }

}