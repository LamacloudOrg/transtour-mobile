package com.transtour.chofer.ui.activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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
                        putExtra("userName", user.userName)
                    }
                    startActivity(intent)
                }else{
                    Toast.makeText(this,"Usuario error",Toast.LENGTH_LONG).show()

                }
        }

         loginViewModel.resultado.observe(this,observer)
         btnLogin =findViewById(R.id.btnLogin)

         btnLogin.setOnClickListener{
            it -> GlobalScope.launch {
             isUser()
            }
         }

     }

     private suspend fun isUser() {
        user.userName = editTextName.text.toString()
        user.password = editTextPassword.text.toString()
         loginViewModel.authenticate(user,applicationContext)
     }

    fun registerAction(v: View) {
        val intent = Intent(v.context,RegisterActivity::class.java)
            startActivity(intent)
    }

}

