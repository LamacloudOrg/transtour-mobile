package com.transtour.chofer.module

import com.transtour.chofer.viewmodel.RegisterViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RegisterModule {
    @Provides
    @Singleton
    fun proviedView() = RegisterViewModel()
}