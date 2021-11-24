package com.transtour.chofer.ui.activitys

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.messaging.FirebaseMessaging
import com.transtour.chofer.App
import com.transtour.chofer.R
import com.transtour.chofer.model.User
import com.transtour.chofer.viewmodel.LoginViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

class LoginActivity() : AppCompatActivity() {
    var user:User = User()
    @Inject  lateinit var loginViewModel:LoginViewModel
    private lateinit var editTextName:EditText
    private lateinit var editTextPassword:EditText
    private lateinit var btnLogin:Button
    private var sharedPref:SharedPreferences? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        (application as App).getComponent().inject(this)
        configView()

        sharedPref = applicationContext?.getSharedPreferences(
            "transtour.mobile", Context.MODE_PRIVATE)

        with(sharedPref?.edit()){
            this?.remove("token");
            this?.apply()
        }

        getAndSetTokenMobileInContext()

    }

     fun  configView(){
         editTextName = findViewById(R.id.editextUserName)
         editTextPassword =findViewById(R.id.editTextPassword)

        val  observer = Observer<Boolean>(){
            isOK ->
                if (isOK){
                    val isConfigured = sharedPref!!.getBoolean("termAndConditions",false)
                    val intent: Intent
                    if(isConfigured){
                        intent = Intent(this@LoginActivity,TravelActivity::class.java).apply {
                            putExtra("dni", user.dni)
                        }
                    }else{
                        intent = Intent(this@LoginActivity,ConfigurationActivity::class.java).apply {
                            putExtra("dni", user.dni)
                        }
                    }
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this,"Usuario error",Toast.LENGTH_LONG).show()
                }
        }

         loginViewModel.resultado.observe(this,observer)
         btnLogin =findViewById(R.id.btnLogin)

         btnLogin.setOnClickListener{
            GlobalScope.launch {
             isUser()
            }
         }

     }

     private suspend fun isUser() {
        user.dni = editTextName.text.toString().toLong()
        user.password = editTextPassword.text.toString()
         loginViewModel.authenticate(user,applicationContext)
     }


    fun getAndSetTokenMobileInContext() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isComplete) {
                val fcmToken = it.result.toString()
                Log.i("fcmToken", fcmToken)

                with(sharedPref?.edit()) {
                    this?.putString("fcmToken", fcmToken)
                    this?.apply()
                }
            }
        }
    }

}

