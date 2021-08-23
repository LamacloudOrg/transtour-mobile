package com.transtour.chofer.service

import android.R
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
import com.transtour.chofer.model.UserRegisterNotification
import com.transtour.chofer.repository.network.userNotification.UserNotificationNetworkAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate


class NotificationService: FirebaseMessagingService() {

    val travelSet = mutableSetOf<String>()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        var TAG: String = "transtour-firebase";

        Log.i("transtour-firebase", "logeando")


        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.i("transtour-firebase", "Message Notification Body: ${it.body}")
            Log.i("transtour-firebase", "title  ${it.title}")

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager


            val notification = android.app.Notification.Builder(applicationContext)
                .setContentTitle(it.title)
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
                travelSet.add(json)
                this?.putStringSet("travelList", travelSet)
                this?.apply()
            }

        }

    }

    override fun onNewToken(fmcToken: String) {
        super.onNewToken(fmcToken);
        Log.i("NEW FCM TOKEN", fmcToken)
        GlobalScope.launch{
            sendToken(fmcToken)
        }
    }

      suspend fun sendToken(token:String){
        try {
            val userId = 1L
            val userNotification = UserRegisterNotification(userId, token)
            val response = UserNotificationNetworkAdapter.generateService(baseContext).registerToken(userNotification)

        } catch (e: Exception) {
        Log.d("Exception notification", e.localizedMessage)
        }
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