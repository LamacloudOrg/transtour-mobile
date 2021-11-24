package com.transtour.chofer.util

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.transtour.chofer.model.LoginError
import com.transtour.chofer.repository.network.LoginError.LoginErrorNetworkAdapter
import com.transtour.chofer.ui.activitys.ErrorActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoggingExceptionHandler(  //private final static String ERROR_FILE = MyAuthException.class.getSimpleName() + ".error";
    private val context: Context
) : Thread.UncaughtExceptionHandler,AppCompatActivity() {
    private val rootHandler: Thread.UncaughtExceptionHandler


    override fun uncaughtException(thread: Thread, ex: Throwable) {
        try {
              val lError = LoginError(
                "",
                "",
                ""
            )

            lError.repoName = "Mobile"
            lError.stackTrace = ex.stackTrace.get(0).toString() + "-"
            lError.stackTrace  += ex.stackTrace.get(1).toString() + "-"
            lError.stackTrace  += ex.stackTrace.get(2).toString() + "-"
            lError.stackTrace  += ex.stackTrace.get(3).toString()

            lError.comment = "Exception en mobile, called for " + ex.javaClass

            apiCall(lError)


            Log.d(TAG, "called for " + ex.javaClass)
            //TODO MOSTAR UN PANTALLA DE ERROR

            val intent = Intent(context, ErrorActivity::class.java)
            startActivity(intent)
            finish()

        } catch (e: Exception) {
            Log.e(TAG, "Exception Logger failed!", e)
        }
    }

    private fun apiCall (lError: LoginError) {
     GlobalScope.launch {
         Log.d(TAG, "send to api insert loginError ")
         LoginErrorNetworkAdapter.generateService(context).insert(lError);
     }
    }

    companion object {
        private val TAG = LoggingExceptionHandler::class.java.simpleName
    }

    init {
        // we should store the current exception handler -- to invoke it for all not handled exceptions ...
        rootHandler = Thread.getDefaultUncaughtExceptionHandler()
        // we replace the exception handler now with us -- we will properly dispatch the exceptions ...
        Thread.setDefaultUncaughtExceptionHandler(this)
    }
}