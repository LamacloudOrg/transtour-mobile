package com.transtour.chofer.service

import android.R
import android.app.Notification
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import java.time.LocalDate


class NotificationService: FirebaseMessagingService() {



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        var TAG: String = "transtour-firebase";

        Log.i("transtour-firebase", "logeando")


        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.i("transtour-firebase", "Message Notification Body: ${it.body}")
            Log.i("transtour-firebase", "title  ${it.title}")

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            val notification = Notification.Builder(applicationContext)
                .setContentTitle(it.title)
                .setContentText("New travel "+ LocalDate.now().toString())
                .setSmallIcon(R.drawable.ic_dialog_alert)
                .setLargeIcon(BitmapFactory.decodeResource(this.resources, R.drawable.ic_dialog_alert))
                .build()

                notificationManager.notify(1234,notification)

            sendNotification(it.title);
        }


        remoteMessage?.data.let {

            val sharedPref = applicationContext?.getSharedPreferences(
                "transtour.mobile", Context.MODE_PRIVATE)


            with (sharedPref?.edit()){
                val gson = com.google.gson.Gson()
                val json = gson.toJson(it)
                this?.putString("travel-new", json.toString())
                this?.apply()
            }

        }

    }

    override fun onNewToken(fmcToken: String) {
        super.onNewToken(fmcToken);
        Log.i("token-firebase", fmcToken)
        //TODO implementar esta funcion
        sendToken(fmcToken)
    }

    private fun sendToken(token:String){
        //TODO registrar token en el servidor
        //TUAdapater.generateService(applicationContext).registrarToken(token)
    }


    private fun sendNotification(data:MutableMap<String,String>) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(Runnable { Toast.makeText(applicationContext,data.toString(),Toast.LENGTH_SHORT).show() })

    }

    private fun sendNotification(title:String?) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(Runnable { Toast.makeText(applicationContext,title!!.toString(),Toast.LENGTH_SHORT).show() })

    }


}