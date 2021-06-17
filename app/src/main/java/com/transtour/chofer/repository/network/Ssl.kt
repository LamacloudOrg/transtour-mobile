package com.transtour.chofer.repository.network

import android.content.Context
import com.transtour.chofer.R
import java.io.FileInputStream
import java.security.KeyStore
import java.security.SecureRandom
import javax.net.ssl.KeyManagerFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory

object Ssl {

    fun generateCetificate(context: Context): SSLSocketFactory? {
        // Get the file of our certificate
        var caFileInputStream = context.resources.openRawResource(R.raw.my_certificate)

     //   var caFileInputStream =  FileInputStream("/src/main/resources/my_certificate")

        // We're going to put our certificates in a Keystore
        var keyStore:KeyStore =KeyStore.getInstance("PKCS12");
        keyStore.load(caFileInputStream, "transtour-api".toCharArray())

        // Create a KeyManagerFactory with our specific algorithm our our public keys
        // Most of the cases is gonna be "X509"

        val keyManagerFactory = KeyManagerFactory.getInstance("X509");
        keyManagerFactory.init(keyStore, "transtour-api".toCharArray());

        // Create a SSL context with the key managers of the KeyManagerFactory
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(keyManagerFactory.keyManagers, null, SecureRandom())

        return sslContext.socketFactory

    }
}