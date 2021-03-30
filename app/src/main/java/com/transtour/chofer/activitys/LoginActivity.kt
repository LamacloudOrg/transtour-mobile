package com.transtour.chofer.activitys

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.transtour.chofer.R
import com.transtour.chofer.model.User
import com.transtour.chofer.repository.network.ApiClientAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.lang.Exception

class LoginActivity : AppCompatActivity() , CoroutineScope by MainScope() {
    var user:User = User()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        user.name = (findViewById(R.id.editextUserName) as TextView).text.toString()
        user.password = (findViewById(R.id.editTextPassword) as TextView).text.toString()
    }

     fun isUser(view: View) {

         launch(Dispatchers.Main) {
             try {
                 val response = ApiClientAdapter.apiClient.login(user)
                 if (response.isSuccessful && response.body() != null) {
                     Toast.makeText(view.rootView.context, "welcome ${user.name}", Toast.LENGTH_LONG).show()

                     val intent = Intent(this@LoginActivity,TravelActivity::class.java).apply {
                         putExtra("userName", user.name)
                     }
                     startActivity(intent)
                 } else {
                     Toast.makeText(view.rootView.context, "Error " + response.errorBody().toString(), Toast.LENGTH_LONG).show()
                 }
             } catch (e: Exception) {
                 Toast.makeText(view.rootView.context, "Error " + e.localizedMessage, Toast.LENGTH_LONG)
                         .show()
             }
         }
     }

}