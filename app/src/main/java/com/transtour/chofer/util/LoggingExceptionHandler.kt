package com.transtour.chofer.util

import android.content.Context
import android.util.Log
import com.transtour.chofer.model.LoginError
import com.transtour.chofer.repository.network.LoginError.LoginErrorNetworkAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

class LoggingExceptionHandler(  //private final static String ERROR_FILE = MyAuthException.class.getSimpleName() + ".error";
    private val context: Context
) : Thread.UncaughtExceptionHandler {
    private val rootHandler: Thread.UncaughtExceptionHandler
    override fun uncaughtException(thread: Thread, ex: Throwable) {
        try {
            val lError = LoginError(
                Date().toString(),
                "aca va el tiempo",
                "mobile",
                ex.stackTrace.toString(),
                ex.message.toString()
            )

            apiCall(lError)

            Log.d(TAG, "called for " + ex.javaClass)
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