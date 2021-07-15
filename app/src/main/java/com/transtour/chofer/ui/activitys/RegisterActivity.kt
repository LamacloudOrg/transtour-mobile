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
import com.transtour.chofer.viewmodel.RegisterViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class RegisterActivity() : AppCompatActivity() {

    var user: User = User()
    @Inject
    lateinit var registerViewModel: RegisterViewModel
    private lateinit var editTextNameUser: EditText
    private lateinit var editTextToken: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        (application as App).getComponent().inject(this)
        configView()
        getAndSetTokenMobileInContext(applicationContext)



    }

    fun configView() {
        //registerViewModel = RegisterViewModel()
        editTextNameUser = findViewById(R.id.editextUserNameRegister)
        editTextToken = findViewById(R.id.editTextPasswordToken)
        editTextPassword = findViewById(R.id.editTextPasswordRegister)

        //TODO update del password en user backend
        val  observer = Observer<Boolean>(){
                isOK ->
            if (isOK){
                Toast.makeText(this,"Usuario ok",Toast.LENGTH_LONG).show()
                val intent = Intent(this@RegisterActivity,LoginActivity::class.java).apply {
                    putExtra("userName", "kike")
                }
                startActivity(intent)
            }else{
                Toast.makeText(this,"Usuario error",Toast.LENGTH_LONG).show()

            }
        }

        registerViewModel!!.resultado.observe(this,observer)
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

        val sharedPref = v.context.getSharedPreferences(
            "transtour.mobile", Context.MODE_PRIVATE)

        val userId:String? = editTextNameUser.text.toString()
        val password:String? = editTextPassword.text.toString()
        var user: UserRegisterNotification = UserRegisterNotification(userId?.toLong(), sharedPref.getString("fcmToken",""))

        GlobalScope.launch{
            if (password != null) {
                registerViewModel!!.execute(user,password,v.context)
            }
        }


        //TODO update del cp en notification backend
    }
}