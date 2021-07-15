package com.transtour.chofer.component

import com.transtour.chofer.module.LoginModule
import com.transtour.chofer.module.RegisterModule
import com.transtour.chofer.ui.activitys.LoginActivity
import com.transtour.chofer.ui.activitys.RegisterActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [LoginModule::class,RegisterModule::class])
interface TranstourComponent {
    fun inject(activity: LoginActivity)
    fun inject(activity: RegisterActivity)

}