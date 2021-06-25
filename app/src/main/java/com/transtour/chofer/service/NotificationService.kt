package com.transtour.chofer.service

import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class NotificationService: FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        var TAG: String = "transtour-firebase";

        Log.i("transtour-firebase", "logeando")

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.i("transtour-firebase", "Message Notification Body: ${it.body}")
            Log.i("transtour-firebase", "title  ${it.title}")
            sendNotification(it.title);
        }

        sendNotification(remoteMessage?.data);


    }

    /*override fun onNewToken(fmcToken: String) {

    }

    private fun sendToken(token:String){

    }*/

    private fun sendNotification(data:MutableMap<String,String>) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(Runnable { Toast.makeText(applicationContext,data.toString(),Toast.LENGTH_SHORT).show() })

    }

    private fun sendNotification(title:String?) {
        val handler = Handler(Looper.getMainLooper())
        handler.post(Runnable { Toast.makeText(applicationContext,title!!.toString(),Toast.LENGTH_SHORT).show() })

    }


}