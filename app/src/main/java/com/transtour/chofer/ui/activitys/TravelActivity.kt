package com.transtour.chofer.ui.activitys

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.transtour.chofer.R
import com.transtour.chofer.model.Signature
import com.transtour.chofer.model.Travel
import com.transtour.chofer.viewmodel.TravelViewModel
import com.transtour.chofer.viewmodel.VoucherViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


class TravelActivity() : AppCompatActivity() {

    val TAG: String = "TravelActivity"
    //@Inject
    lateinit var travelViewModel: TravelViewModel
    //@Inject
    lateinit var voucherViewModel: VoucherViewModel
    lateinit var tvViajeHora: TextView
    lateinit var tvViajeFecha: TextView
    lateinit var tvPassenger: TextView
    lateinit var tvOrigin :TextView
    lateinit var tvDestiny:TextView
    lateinit var tvObservation:TextView
    lateinit var btnFinisih:Button
    lateinit var  btnRefreshTravel:Button



    var passanger: String? = null
    var date: String? = null
    val SECOND_ACTIVITY_REQUEST_CODE:Int =1
    var travelId:String = ""


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
        voucherViewModel = VoucherViewModel()
        tvViajeHora =  findViewById(R.id.tvDate)
        tvViajeFecha = findViewById(R.id.tvDate)
        tvPassenger =  findViewById(R.id.tvPassenger)
        tvOrigin=      findViewById(R.id.tvOrigin)
        tvDestiny =   findViewById(R.id.tvDestiny)
        tvObservation = findViewById(R.id.tvObeservation)
        btnRefreshTravel = findViewById(R.id.btnRefreshTravel)
        btnFinisih = findViewById(R.id.btnFinish)

        btnFinisih.setOnClickListener{
            val intent = Intent(applicationContext, SignatureActivity::class.java)
            startActivityForResult(intent,SECOND_ACTIVITY_REQUEST_CODE)
        }


        val  observer = Observer<Travel>(){
            travel -> display(travel)
        }

        val  observerSignature = Observer<Boolean>(){
                isOk ->
                if (isOk){
                    Toast.makeText(applicationContext,"La firma se impacto correctamente",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext,"No se pudo impactar intente nuevamente",Toast.LENGTH_LONG).show()
                }
        }

        travelViewModel!!.resultado.observe(this,observer)

        voucherViewModel!!.resultado.observe(this,observerSignature)

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
        travelId = travel.id.toString()

    }


    fun convertToBase64(attachment: File): String {
        return Base64.encodeToString(attachment.readBytes(), Base64.NO_WRAP)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       super.onActivityResult(requestCode, resultCode, data)

        // check that it is the SecondActivity with an OK result
        if (requestCode == SECOND_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) { // Activity.RESULT_OK

                // get String data from Intent
                val path = data?.getStringExtra("path")

                val file = File(path)
                val base64 =convertToBase64(file)
                val signature = Signature(travelId,base64,"png")

                Log.d(TAG,"Impactando firma travel ${travelId}")
                GlobalScope.launch {
                    voucherViewModel.sign(signature,application)
                }
            }
        }
    }

}
