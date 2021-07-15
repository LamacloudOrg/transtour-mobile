package com.transtour.chofer.repository.network

import android.content.Context
import com.transtour.chofer.R
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory


object Ssl {

    fun generateCetificate(context: Context): SSLContext? {


        // Loading CAs from an InputStream
        // Loading CAs from an InputStream
        var cf: CertificateFactory? = null
        cf = CertificateFactory.getInstance("X.509")

        var ca: Certificate
        // I'm using Java7. If you used Java6 close it manually with finally.
        context.resources.openRawResource(R.raw.my_certificate).use { cert ->
            ca = cf.generateCertificate(cert)
        }

        // Creating a KeyStore containing our trusted CAs

        // Creating a KeyStore containing our trusted CAs
        val keyStoreType = KeyStore.getDefaultType()
        val keyStore = KeyStore.getInstance(keyStoreType)
        keyStore.load(null, null)
        keyStore.setCertificateEntry("ca", ca)

        // Creating a TrustManager that trusts the CAs in our KeyStore.

        // Creating a TrustManager that trusts the CAs in our KeyStore.
        val tmfAlgorithm: String = TrustManagerFactory.getDefaultAlgorithm()
        val tmf: TrustManagerFactory = TrustManagerFactory.getInstance(tmfAlgorithm)
        tmf.init(keyStore)

        // Creating an SSLSocketFactory that uses our TrustManager

        // Creating an SSLSocketFactory that uses our TrustManager
        val sslContext = SSLContext.getInstance("TLS")
        sslContext.init(null, tmf.getTrustManagers(), null)

        return sslContext

    }
}