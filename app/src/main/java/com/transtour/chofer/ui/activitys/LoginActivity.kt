package com.transtour.chofer.ui.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.transtour.chofer.App
import com.transtour.chofer.R
import com.transtour.chofer.model.User
import com.transtour.chofer.viewmodel.LoginViewModel
import dagger.Component
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LoginActivity() : AppCompatActivity() {
    var user:User = User()
    @Inject  lateinit var loginViewModel:LoginViewModel
    private lateinit var editTextName:EditText
    private lateinit var editTextPassword:EditText
    private lateinit var btnLogin:Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        (application as App).getComponent().inject(this)
        configView()
    }

     fun  configView(){
         editTextName = findViewById(R.id.editextUserName)
         editTextPassword =findViewById(R.id.editTextPassword)

        val  observer = Observer<Boolean>(){
            isOK ->
                if (isOK){
                    Toast.makeText(this,"Usuario ok",Toast.LENGTH_LONG).show()
                    val intent = Intent(this@LoginActivity,TravelActivity::class.java).apply {
                        putExtra("userName", user.name)
                    }
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Usuario error",Toast.LENGTH_LONG).show()

                }
        }
         loginViewModel!!.resultado.observe(this,observer)
         btnLogin =findViewById(R.id.btnLogin)

         btnLogin.setOnClickListener{
            it -> GlobalScope.launch {
             isUser()
            }
         }

     }

     suspend fun isUser() {
        user.name = editTextName?.text.toString()
        user.password = editTextPassword?.text.toString()
         loginViewModel?.authenticate(user,getApplicationContext())
     }



}

