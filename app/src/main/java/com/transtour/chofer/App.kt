package com.transtour.chofer

import android.app.Application
import com.transtour.chofer.component.DaggerTranstourComponent
import com.transtour.chofer.component.TranstourComponent
import com.transtour.chofer.module.LoginModule


class App:Application() {

    private lateinit var transtourComponent: TranstourComponent

    override fun onCreate() {
        super.onCreate()
        transtourComponent = DaggerTranstourComponent.builder().loginModule(LoginModule()).build()
    }

    fun getComponent()= transtourComponent
}