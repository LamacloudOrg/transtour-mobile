package com.transtour.chofer.repository.network

import android.content.Context
import com.google.android.gms.common.util.IOUtils
import com.transtour.chofer.R
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory


object Ssl {

    fun generateCetificate(): SSLContext? {


        // Loading CAs from an InputStream
        // Loading CAs from an InputStream
        var cf: CertificateFactory? = null
        cf = CertificateFactory.getInstance("X.509")

        var ca: Certificate
        // I'm using Java7. If you used Java6 close it manually with finally.

        val sCert:String = """
        -----BEGIN CERTIFICATE-----
        MIIDlzCCAn+gAwIBAgIEIlYP8TANBgkqhkiG9w0BAQsFADB8MQswCQYDVQQGEwJB 
        UjEVMBMGA1UECBMMYnVlbm9zIGFpcmVzMRcwFQYDVQQHEw5ncmFsIGxhIG1hZHJp
        ZDESMBAGA1UEChMJbGFtYWNsb3VkMRIwEAYDVQQLEwlsYW1hY2xvdWQxFTATBgNV
        BAMTDHBhYmxvIG9jYW50bzAeFw0yMTA2MTAyMjMwMjhaFw0yMjA2MDUyMjMwMjha
        MHwxCzAJBgNVBAYTAkFSMRUwEwYDVQQIEwxidWVub3MgYWlyZXMxFzAVBgNVBAcT
        DmdyYWwgbGEgbWFkcmlkMRIwEAYDVQQKEwlsYW1hY2xvdWQxEjAQBgNVBAsTCWxh
        bWFjbG91ZDEVMBMGA1UEAxMMcGFibG8gb2NhbnRvMIIBIjANBgkqhkiG9w0BAQEF
        AAOCAQ8AMIIBCgKCAQEAkyJSsz0wH5sFFfMU9cMeLtOm09zOASyyggs9H7tfYMWL
        8Pc/zOrZhLXc+fHYoR18UfCKwl2o+e5tNfF/yy7s6Uv6zCrYOWYAZJSPtSIvyKib
        GXxLqjspgBuis6wBUCw5i6qs8GM5yFLtWT7XiEOj2wj7gXAz4/zElH3ybKVSRskn
        DsGh7uakxKW1n9YZJSeA04ylDh1wWVv13T4PYRhuR8nUNqLEg66Z/BNYBH95R0Fk
        Czh8qESXXGGO/+71ZfAoqnBpg5Nte7T2ZEmz9eNS8BSwCpe0LFaG9iXc+18xtiqE
        vjo5m880k+wTXxAv7OWYInOdt9VPDWfdXyjCatrkJwIDAQABoyEwHzAdBgNVHQ4E
        FgQUHcvC+hsElTqKlrl8hrzsupPU/HAwDQYJKoZIhvcNAQELBQADggEBACg+xTFh
        3058XKt2oaFbn/US46kSgYwngSE6B0J/oTHG9RFejPaFIMV27ewXRQAXj/rnQgvS
        vM2UPigL+zvkhEbzz5x8rhrc7zPXbR9jyYdQUZGNWBroJ6XHymiyst+vlV5tSiy2
        TZPCx75WWSDMMMXwYULEOJR2nJzKhQ06DTaihkP3EOBZyioNwrb/NqSLdTOiSYFJ
        cXTq69NcSPUGKv8TJCSYpfnNApdoBNVc8wte17PaxNgQ7/IwcJRX5LOD7UXgTMQ/
        OvbcKKbgdv6AQy2+oEsEOwkuwtEpedpg0tV+esacSRGTbR8iqh8/FSfrzwHryp8x
        63ywvMoNWKRhPH0=
        -----END CERTIFICATE-----
            """.trimIndent()

        //  Ssl::class.java.getResourceAsStream("raw/my_certificate").use { cert ->
        val inputStream: InputStream = ByteArrayInputStream(sCert.toByteArray(Charsets.UTF_8))
        ca = cf.generateCertificate(inputStream)
       // }

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