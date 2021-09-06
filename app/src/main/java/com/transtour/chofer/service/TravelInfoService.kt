package com.transtour.chofer.service

import android.annotation.SuppressLint
import android.app.job.JobParameters
import android.app.job.JobService
import android.util.Log
import com.transtour.chofer.repository.network.travel.TravelNetworkAdapter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

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
        println("job")
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        jobCancelled = true;
        return true;
    }
}