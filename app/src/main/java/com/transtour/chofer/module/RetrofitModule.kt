package com.transtour.chofer.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder
import com.transtour.chofer.repository.network.CustomInterceptor
import com.transtour.chofer.repository.network.EndPointApi
import com.transtour.chofer.repository.network.Ssl

import javax.inject.Singleton;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import dagger.Module;
import dagger.Provides;
import dagger.Reusable
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory

@Module
public class RetrofitModule {
    @Provides
    @Singleton
    fun gson():Gson = GsonBuilder()
        .setLenient()
        .create()

    @Provides
    @Singleton
    fun httpInterceptor():Interceptor= CustomInterceptor()

    @Provides
    @Singleton
    fun allHostsValid():HostnameVerifier= HostnameVerifier { _, _ -> true }

    @Provides
    @Singleton
    fun sslScketFactory():SSLSocketFactory? = Ssl.generateCetificate()?.socketFactory

    @Provides
    @Singleton
    fun httpClient(allHostsValid:HostnameVerifier, customInterceptor: Interceptor,sslSocketFactory:SSLSocketFactory?):OkHttpClient =
        OkHttpClient.Builder().apply {
        addNetworkInterceptor(customInterceptor)
        sslSocketFactory(sslSocketFactory)
        hostnameVerifier(allHostsValid)
    }.build()

    @Provides
    @Singleton
    fun retrofit(httpClient:OkHttpClient,gson: Gson):Retrofit=Retrofit.Builder()
            .baseUrl(EndPointApi.getEndPoint("prod"))
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()


}
