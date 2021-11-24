package com.transtour.chofer.ui.activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import com.transtour.chofer.App
import com.transtour.chofer.R
import com.transtour.chofer.viewmodel.RegisterViewModel
import androidx.lifecycle.Observer
import com.transtour.chofer.model.UserRegisterNotification
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates

class ConfigurationActivity : AppCompatActivity() {

    @Inject
    lateinit var registerViewModel: RegisterViewModel
    private var sharedPref: SharedPreferences? = null
    private var userId by Delegates.notNull<Long>()
    private lateinit var fcmToken:String

    private lateinit var editTextTextMultiLine:TextView
    private lateinit var textViewCheckBox: CheckBox



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        sharedPref = applicationContext?.getSharedPreferences(
            "transtour.mobile", Context.MODE_PRIVATE)

        userId = intent.getLongExtra("dni",1)
        fcmToken = sharedPref?.getString("fcmToken","").toString()

        (application as App).getComponent().inject(this)
        configView()
    }


    fun configView(){
        editTextTextMultiLine = findViewById(R.id.editTextTextMultiLine)
        textViewCheckBox = findViewById(R.id.checkedTextView)

        editTextTextMultiLine.setText("""
            La apliacion transtour requeiere hacer uso
            de uso de datos personales como localizacion,numero de de telefono, y otro datos.
            Para poder usar la aplicacion es necesario que acepte los terminos
            y condiciones desciptos anteriormente para hacer uso de la aplicacion
        """.trimIndent(),TextView.BufferType.SPANNABLE)


        textViewCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                val userRegisterNotification = UserRegisterNotification(userId,fcmToken)
                GlobalScope.launch {
                    registerViewModel.registToken(userRegisterNotification,applicationContext)
                }
            }
        }

        val observer = Observer<Boolean> { isOK ->
            if (isOK) {
                Toast.makeText(this, "Usuario ok", Toast.LENGTH_LONG).show()
                val intent = Intent(this@ConfigurationActivity, TravelActivity::class.java).apply {
                    putExtra("userName", "kike")
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "ocurrio un error", Toast.LENGTH_LONG).show()

            }
        }
        registerViewModel.resultado.observe(this, observer)



    }
}


