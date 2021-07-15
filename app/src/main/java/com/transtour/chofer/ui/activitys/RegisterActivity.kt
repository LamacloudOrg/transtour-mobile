package com.transtour.chofer.ui.activitys

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        (application as App).getComponent().inject(this)
        configView()
        getAndSetTokenMobileInContext(applicationContext)
    }

    fun configView() {
        editTextNameUser = findViewById(R.id.editextUserNameRegister)
        editTextToken = findViewById(R.id.editTextPasswordToken)
        editTextPassword = findViewById(R.id.editTextPasswordRegister)

        val observerRegistration = Observer<Boolean> { isOK ->
            if (isOK) {
                Toast.makeText(this, "Usuario ok", Toast.LENGTH_LONG).show()
                val intent = Intent(this@RegisterActivity, LoginActivity::class.java).apply {
                    putExtra("userName", "kike")
                }
                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuario error", Toast.LENGTH_LONG).show()

            }
        }

        /*
        val observerToken = Observer<String> { token ->
            if (token.isNotEmpty()) {
                editTextToken.setText(token)
            } else {
                editTextToken.setText("")
            }
        }
        */

        registerViewModel.resultado.observe(this, observerRegistration)
    //    registerViewModel.token.observe(this, observerToken)
    }

   //@RequiresApi(Build.VERSION_CODES.GINGERBREAD)
    fun getAndSetTokenMobileInContext(context: Context) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            if (it.isComplete) {
                val fcmToken = it.result.toString()
                Log.i("fcmToken", fcmToken)

                val sharedPref = context.getSharedPreferences(
                    "transtour.mobile", Context.MODE_PRIVATE
                )
                with(sharedPref.edit()) {
                    putString("fcmToken", fcmToken)
                    apply()
                }
            }
        }
    }

    fun registerAction(v: View) {
        Log.i("RegisterActivity", "registrando usuario")

        val sharedPref = v.context.getSharedPreferences(
            "transtour.mobile", Context.MODE_PRIVATE
        )

        val userId: String = editTextNameUser.text.toString()
        val password: String = editTextPassword.text.toString()
        val token: String = editTextToken.text.toString()
        val user = UserRegisterNotification(userId.toLong(), sharedPref.getString("fcmToken", ""))

        if (userId.isBlank()){
            editTextNameUser.setError("No se puede ser vacio",null)
            return
        }

        if (password.isBlank()){
            editTextPassword.setError("No se puede ser vacio",null)
            return
        }

        if (token.isBlank()){
            editTextToken.setError("No se puede ser vacio",null)
            return
        }

        GlobalScope.launch {
            registerViewModel.registUser(user, password, v.context)
        }

    }

    /*
    fun getTokenRegistration(view: View) {
        val userId: String = editTextNameUser.text.toString()

        if (userId.isBlank()){
            editTextNameUser.setError("No se puede ser vacio",null)
            return
        }

        GlobalScope.launch {
            registerViewModel.tokenRegistration()
        }

    }
    */

}