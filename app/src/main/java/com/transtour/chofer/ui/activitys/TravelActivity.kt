package com.transtour.chofer.ui.activitys

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.transtour.chofer.R
import com.transtour.chofer.model.Signature
import com.transtour.chofer.model.Travel
import com.transtour.chofer.model.TravelTaxes
import com.transtour.chofer.util.BaseActivity
import com.transtour.chofer.viewmodel.TravelViewModel
import com.transtour.chofer.viewmodel.VoucherViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject


class TravelActivity() : BaseActivity() {

    val TAG: String = "TravelActivity"
    //@Inject
    lateinit var travelViewModel: TravelViewModel
    //@Inject
    lateinit var voucherViewModel: VoucherViewModel
    lateinit var tvViajeHora: EditText
    lateinit var tvViajeFecha: EditText
    lateinit var tvPassenger: EditText
    lateinit var tvOrigin :EditText
    lateinit var tvDestiny:EditText
    lateinit var tvObservation:EditText
    lateinit var tvtextMontoViaje:TextView
    lateinit var tvCompany:TextView
    lateinit var tvtextCotoTotal:TextView
    lateinit var  etWaitingTime:EditText
    lateinit var  etToll:EditText
    lateinit var  etparkingAmount:EditText
    lateinit var editTextTaxForReturn:EditText
    lateinit var editTextTaxAmount:EditText
    lateinit var  tvtotalCost:EditText
    lateinit var btnFinisih:Button
    lateinit var  btnRefreshTravel:Button
    lateinit var  btnLogout:Button



    var totalNetAmount:Double = 0.00
    var passanger: String? = null
    var date: String? = null
    val SECOND_ACTIVITY_REQUEST_CODE:Int =1
    var travelId:String = ""
    var travelTaxes:TravelTaxes = TravelTaxes("","","","","")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_travel)

       // val intent:Intent = intent
       //  val user: Int? = intent.getIntExtra("userName");

        passanger = intent.getStringExtra("passanger")
        date = intent.getStringExtra("date")

        val sharedPref = applicationContext?.getSharedPreferences(
            "transtour.mobile", Context.MODE_PRIVATE)

        val token = sharedPref?.getString("token-user","")

        token?.let { Log.d("token-user", it) }

        configView()
    }

    override fun onResume() {
        super.onResume()
        clearAll()
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
        etWaitingTime =findViewById(R.id.editTextWaitingTime)
        etToll =findViewById(R.id.editTextToll)
        etparkingAmount =findViewById(R.id.editTextParking)
        editTextTaxForReturn = findViewById(R.id.editTextTaxForReturn)
        editTextTaxAmount = findViewById(R.id.editTextTaxAmount)
        tvtotalCost = findViewById(R.id.tvTotalCost)
        tvtextMontoViaje = findViewById(R.id.tvtextMontoViaje)
        tvtextCotoTotal = findViewById(R.id.tvtextCotoTotal)
        tvCompany = findViewById(R.id.tvCompany)

        btnFinisih.setOnClickListener{

            if (etWaitingTime.text.toString().isNullOrEmpty()){
                etWaitingTime.error = "No puedo ser vacio"
                return@setOnClickListener
            }

            if (etToll.text.toString().isNullOrEmpty()){
                etToll.error = "No puedo ser vacio"
                return@setOnClickListener
            }

            if (etparkingAmount.text.toString().isNullOrEmpty()){
                etparkingAmount.error = "No puedo ser vacio"
                return@setOnClickListener
            }

            if (editTextTaxForReturn.text.toString().isNullOrEmpty()){
                editTextTaxForReturn.error = "No puedo ser vacio"
                return@setOnClickListener
            }


            GlobalScope.launch {
                Log.d("Travel taxex",travelTaxes.toString());
                voucherViewModel.updateTaxes(travelTaxes,applicationContext)
            }

        }
        etWaitingTime.setOnFocusChangeListener{
            _,hasFocus -> if(!hasFocus){
            travelTaxes.waitingTime = etWaitingTime.text.toString()
            updateCost(etWaitingTime.text.toString())
            }else{
                resetCost(etWaitingTime.text.toString())
            }
        }

        etToll.setOnFocusChangeListener{
                _,hasFocus -> if(!hasFocus){
                travelTaxes.tolAmount = etToll.text.toString()
                updateCost(etToll.text.toString())
            }else{
                resetCost(etToll.text.toString())
            }
        }


        etparkingAmount.setOnFocusChangeListener{
                _,hasFocus -> if(!hasFocus){
                travelTaxes.parkingAmount = etparkingAmount.text.toString()
                updateCost(etparkingAmount.text.toString())
            }
        }

        editTextTaxForReturn.setOnFocusChangeListener{
                _,hasFocus -> if(!hasFocus){
                    travelTaxes.taxForReturn = editTextTaxForReturn.text.toString()
                    updateCost(editTextTaxForReturn.text.toString())
                }else{
                    resetCost(editTextTaxForReturn.text.toString())
                }
        }


        val  observer = Observer<Travel>(){
            travel -> display(travel)
        }

        val  signatureObserver = Observer<Boolean>(){
                isOk ->
                if (isOk){
                    clearAll()
                    removeTravel()
                    Toast.makeText(applicationContext,"La firma se impacto correctamente",Toast.LENGTH_LONG).show()
                }else{
                    Toast.makeText(applicationContext,"No se pudo impactar intente nuevamente",Toast.LENGTH_LONG).show()
                }
        }

        val  travelObserver = Observer<Boolean>(){
                isOk ->
            if (isOk){
                val intent = Intent(applicationContext, SignatureActivity::class.java)
                startActivityForResult(intent,SECOND_ACTIVITY_REQUEST_CODE)
            }else{
                Toast.makeText(applicationContext,"Error con la Actualizacio/Intente nuevamente.",Toast.LENGTH_LONG).show()
            }
        }

        travelViewModel!!.resultado.observe(this,observer)

        voucherViewModel!!.signerResult.observe(this,signatureObserver)
        voucherViewModel!!.travelResult.observe(this,travelObserver)

        btnRefreshTravel.setOnClickListener{
            it -> GlobalScope.launch {
                getTravel()
            }
        }


    }


    private fun removeTravel() {
        travelViewModel.removeTravel(applicationContext)
    }

    private fun clearAll() {
        tvViajeHora.isEnabled = true
        tvViajeHora.setText("")
        tvViajeHora.isEnabled = false
        tvViajeFecha.isEnabled = true
        tvViajeFecha.setText("")
        tvViajeFecha.isEnabled = false
        tvPassenger.isEnabled = true
        tvPassenger.setText("")
        tvPassenger.isEnabled = false
        tvOrigin.isEnabled = true
        tvOrigin.setText("")
        tvOrigin.isEnabled = false
        tvDestiny.isEnabled = true
        tvDestiny.setText("")
        tvDestiny.isEnabled = false
        tvObservation.isEnabled = true
        tvObservation.setText("")
        tvObservation.isEnabled = false
        etWaitingTime.setText("")
        etToll.setText("")
        etparkingAmount.setText("")
        editTextTaxForReturn.setText("")
        tvtotalCost.isEnabled = true
        tvtotalCost.visibility = View.INVISIBLE
        tvtotalCost.setText("")
        editTextTaxAmount.isEnabled = true
        editTextTaxAmount.setText("")
        editTextTaxAmount.isEnabled = false
        editTextTaxAmount.visibility = View.INVISIBLE
        tvtextCotoTotal.visibility = View.INVISIBLE
        tvtextMontoViaje.visibility = View.INVISIBLE

    }


     fun getTravel() {
        travelViewModel.getTravel(applicationContext)
    }


     fun display(travel:Travel){
        tvViajeFecha.setText(travel.date + " "+travel.hour)
        tvPassenger.setText(travel.passenger)
        tvOrigin.setText(travel.origin)
        tvDestiny.setText( travel.destiny)
        tvObservation.setText(travel.observation)
        travelId = travel.id.toString()
        travelTaxes.id = travel.id.toString()
        if (!travel.netAmount.isNullOrEmpty()) {
            totalNetAmount = travel.netAmount?.toDouble()!!
        }else{
            totalNetAmount +="0.00".toDouble()
        }
        tvtotalCost.setText(getTotalTravelAmount(travel))
        editTextTaxAmount.setText(travel.netAmount)
        etWaitingTime.setText(travel.waitingTime)
        etToll.setText(travel.toll)
        etparkingAmount.setText(travel.parkingAmount)
        editTextTaxForReturn.setText(travel.takForReturn)
        travelTaxes.taxForReturn = travel.takForReturn
        travelTaxes.parkingAmount = travel.parkingAmount
        travelTaxes.tolAmount = travel.toll
        travelTaxes.waitingTime = travel.waitingTime
        tvCompany.text = travel.company

        if (showTotal(travel.company?.toLowerCase())){
        tvtotalCost.visibility = View.VISIBLE
        editTextTaxAmount.visibility = View.VISIBLE
        tvtextCotoTotal.visibility = View.VISIBLE
        tvtextMontoViaje.visibility = View.VISIBLE
        }else{
        tvtotalCost.visibility = View.INVISIBLE
        editTextTaxAmount.visibility = View.INVISIBLE
        tvtextCotoTotal.visibility = View.INVISIBLE
        tvtextMontoViaje.visibility = View.INVISIBLE
       }

    }

    private fun showTotal(company: String?): Boolean {
        if (company == null) return false;
        if (company?.equals("covance")) return  true;
        if (company?.equals("particular")) return  true;

        return false;

    }

    private fun getTotalTravelAmount(travel: Travel): String {
        var totalCost  = "0.00".toDouble()

        if (travel?.netAmount?.isNotEmpty() == true) {
            totalCost += travel.netAmount!!.toDouble()
        }
        if (travel?.toll?.isNotEmpty() == true) {
            totalCost += travel.toll!!.toDouble()
        }

        if (travel?.parkingAmount?.isNotEmpty() == true) {
            totalCost += travel.parkingAmount!!.toDouble()
        }

        if (travel?.takForReturn?.isNotEmpty() == true) {
            totalCost += travel.takForReturn!!.toDouble()
        }

        if (travel?.waitingTime?.isNotEmpty() == true) {
            totalCost += travel.waitingTime!!.toDouble()
        }

        return  totalCost.toString()
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

                Log.d("TravelActivity-path",path.toString())

                val file = File(path)
                val base64 =convertToBase64(file)
                val signature = Signature(travelId,base64,"png")
                file.delete()

                Log.d(TAG,"Impactando firma travel ${travelId}")
                GlobalScope.launch {
                    voucherViewModel.sign(signature,application)
                }
            }
        }
    }

    private fun updateCost(tax: String?) {

        val totalCost = tvtotalCost.text.toString()
        var totalCost_ = "0.00".toDouble()
        if (!totalCost.isNullOrEmpty()){
            totalCost_ += totalCost.toDouble()
        }

        tax?.let {
            if (!tax.isEmpty()) {
                totalCost_ += it.toDouble()
                tvtotalCost.setText(totalCost_.toString())
            }
        }


    }



    private fun resetCost(tax: String) {
        val totalCost = tvtotalCost.text.toString()
        var totalCost_ = "0.00".toDouble()
        if (!totalCost.isNullOrEmpty()){
            totalCost_ += totalCost.toDouble()
        }
        if ( !tax.isNullOrEmpty())    {
            totalCost_ -= tax.toDouble()
            tvtotalCost.setText(totalCost_.toString())
        }

    }

}
