package com.transtour.chofer.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.transtour.chofer.repository.network.CustomInterceptor
import com.transtour.chofer.repository.network.EndPointApi
import com.transtour.chofer.repository.network.Ssl
import com.transtour.chofer.repository.network.user.ApiClient
import com.transtour.chofer.viewmodel.LoginViewModel
import dagger.Module
import dagger.Provides
import dagger.Reusable
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSocketFactory

@Module
class LoginModule() {

    @Provides
    @Singleton
    @Named("apiClientUser")
    fun userApi(retrofit:Retrofit) = retrofit.create(ApiClient::class.java)


    @Provides
    @Singleton
    fun proviedView(@Named("apiClientUser") api: ApiClient) = LoginViewModel(api)

}