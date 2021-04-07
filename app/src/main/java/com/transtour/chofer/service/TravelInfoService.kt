package com.transtour.chofer.service

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.transtour.chofer.repository.network.travel.TravelNetworkAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Exception

@SuppressLint("NewApi")
class TravelInfoService: JobService() {

    val TAG:String = "TravelInfoService"
    var jobCancelled:Boolean = false


    override fun onStartJob(params: JobParameters?): Boolean {
        Log.i(TAG, "Job started");
        doBackgroundWork(params);
        return true;
    }

    private fun doBackgroundWork(params: JobParameters?) {
        GlobalScope.launch {
            try {

                val response = TravelNetworkAdapter.apiClient.lastTravel(true)
                if (response.isSuccessful){
            //        Log.i(TAG,"last travel ${response.body()} ")
                }else{
              //      Log.d(TAG,"no se pudo recuperar el viaje  ${response.errorBody()} ")
                    jobCancelled = true
                }

               // Log.i(TAG, "Job finished");
                jobFinished(params, false);

            }catch (e:Exception){
                e.stackTrace
               // Log.i(TAG,"error  ${e.localizedMessage} ")
            }

        }

    }

    override fun onStopJob(params: JobParameters?): Boolean {
        jobCancelled = true;
        return true;
    }
}