package com.transtour.chofer.service

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
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
        Log.d(TAG, "Job started");
        doBackgroundWork(params);
        return true;
    }

    private fun doBackgroundWork(params: JobParameters?) {
        GlobalScope.launch {
            try {
                delay(1000)

                if (jobCancelled){
                    return@launch
                }


                val response = TravelNetworkAdapter.apiClient.lastTravel(true)
                if (response.isSuccessful){
                    Log.d(TAG,"last travel ${response.body()} ")
                }else{
                    Log.d(TAG,"no se pudo recuperar el viaje  ${response.errorBody()} ")
                    jobCancelled = true
                }

                Log.d(TAG, "Job finished");
                jobFinished(params, false);

            }catch (e:Exception){
                e.stackTrace
                Log.d(TAG,"error  ${e.localizedMessage} ")
            }

        }

    }

    override fun onStopJob(params: JobParameters?): Boolean {
        jobCancelled = true;
        return true;
    }
}