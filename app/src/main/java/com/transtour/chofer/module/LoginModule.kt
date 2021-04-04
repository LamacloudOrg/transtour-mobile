package com.transtour.chofer.module

import com.transtour.chofer.viewmodel.LoginViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LoginModule {
    @Provides
    @Singleton
    fun proviedView() = LoginViewModel()
}