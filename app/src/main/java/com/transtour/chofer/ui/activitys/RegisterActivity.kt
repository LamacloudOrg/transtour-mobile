package com.transtour.chofer.ui.activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.messaging.FirebaseMessaging
import com.transtour.chofer.App
import com.transtour.chofer.R
import com.transtour.chofer.model.User
import com.transtour.chofer.model.UserRegisterNotification
import com.transtour.chofer.service.NotificationService
import com.transtour.chofer.viewmodel.LoginViewModel
import javax.inject.Inject

class RegisterActivity() : AppCompatActivity() {

    var user: User = User()

  //  @Inject
  //  lateinit var loginViewModel: LoginViewModel
    private lateinit var editTextNameUser: EditText
    private lateinit var editTextToken: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
      //  (application as App).getComponent().inject(this)
        configView()
        getAndSetTokenMobileInContext(applicationContext)
    }

    fun configView() {
        editTextNameUser = findViewById(R.id.editextUserNameRegister)
        editTextToken = findViewById(R.id.editTextPasswordToken)
        editTextPassword = findViewById(R.id.editTextPasswordRegister)
    }

    fun getAndSetTokenMobileInContext (context:Context){
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if(it.isComplete){
                var fcmToken = it.result.toString()
                Log.i("fcmToken", fcmToken)

                val sharedPref = context?.getSharedPreferences(
                    "transtour.mobile", Context.MODE_PRIVATE)
                with (sharedPref.edit()) {
                    putString("fcmToken", fcmToken)
                    apply()
                }
            }
        }
    }

    fun registerAction(v: View) {
        Log.i("hola", "hola")

        var user: UserRegisterNotification = UserRegisterNotification(123L, "123asd")

        //TODO update del password en user backend
        val  observer = Observer<Boolean>(){
                isOK ->
            if (isOK){
                Toast.makeText(this,"Usuario ok",Toast.LENGTH_LONG).show()
                val intent = Intent(this@RegisterActivity,LoginActivity::class.java).apply {
                    putExtra("userName", user.userName)
                }
                startActivity(intent)
            }else{
                Toast.makeText(this,"Usuario error",Toast.LENGTH_LONG).show()

            }
        }

        loginViewModel!!.resultado.observe(this,observer)


        //TODO update del cp en notification backend
    }
}