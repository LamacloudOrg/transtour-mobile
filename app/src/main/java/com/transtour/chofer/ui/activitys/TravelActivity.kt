package com.transtour.chofer.ui.activitys

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.DocumentsContract
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.transtour.chofer.R
import com.transtour.chofer.model.Travel
import com.transtour.chofer.viewmodel.TravelViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class TravelActivity() : AppCompatActivity() {

    val TAG: String = "TravelActivity"
    lateinit var travelViewModel: TravelViewModel
    lateinit var tvViajeHora: TextView
    lateinit var tvViajeFecha: TextView
    lateinit var tvPassenger: TextView
    lateinit var tvOrigin :TextView
    lateinit var tvDestiny:TextView
    lateinit var tvObservation:TextView

    var passanger: String? = null
    var date: String? = null

    lateinit var  btnRefreshTravel:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel)

        val intent:Intent = intent
        val user: String? = intent.getStringExtra("userName");

        passanger = intent.getStringExtra("passanger")
        date = intent.getStringExtra("date")

        val sharedPref = applicationContext?.getSharedPreferences(
            "transtour.mobile", Context.MODE_PRIVATE)

        val token = sharedPref?.getString("token-user","")

        token?.let { Log.d("token-user", it) }


        val textViewChofer:TextView = findViewById(R.id.tvChoferNombre)
        textViewChofer.text = user

        configView()
    }

    override fun onResume() {
        super.onResume()
        GlobalScope.launch {
            getTravel()
        }

    }


    fun  configView(){

        travelViewModel = TravelViewModel()
        tvViajeHora =  findViewById(R.id.tvDate)
        tvViajeFecha = findViewById(R.id.tvDate)
        tvPassenger =  findViewById(R.id.tvPassenger)
        tvOrigin=      findViewById(R.id.tvOrigin)
        tvDestiny =   findViewById(R.id.tvDestiny)
        tvObservation = findViewById(R.id.tvObeservation)
        btnRefreshTravel = findViewById(R.id.btnRefreshTravel)



        val  observer = Observer<Travel>(){
            travel -> display(travel)
        }
        travelViewModel!!.resultado.observe(this,observer)

        btnRefreshTravel.setOnClickListener{
            it -> GlobalScope.launch {
                getTravel()
            }
        }


    }


    suspend fun getTravel() {
        travelViewModel.getTravel(applicationContext)
    }


     fun display(travel:Travel){
        tvViajeFecha.text = travel.date + " "+travel.hour
        tvPassenger.text = travel.passenger
        tvOrigin.text = travel.origin
        tvDestiny.text = travel.destiny
        tvObservation.text = "No aplica"

    }


    fun signDocument(view: View) {
        val intent = Intent(view.context, SignatureActivity::class.java)
        startActivity(intent)
    }

}