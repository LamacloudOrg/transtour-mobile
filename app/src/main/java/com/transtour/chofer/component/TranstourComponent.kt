package com.transtour.chofer.component

import com.transtour.chofer.module.LoginModule
import com.transtour.chofer.ui.activitys.LoginActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [LoginModule::class])
interface TranstourComponent {
    fun inject(activity: LoginActivity)

}