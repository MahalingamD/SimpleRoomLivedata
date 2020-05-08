package com.ers.tkenterprise.apiUtils

import com.maha.simpleroom.webservice.WebserviceInterface
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by mahalingam
 */

object ApiClient {

    private var retrofit: Retrofit? = null
    private val mInterceptor = HttpLoggingInterceptor()
    private val okHttpClient: OkHttpClient = OkHttpClient().newBuilder().connectTimeout(15, TimeUnit.MINUTES).readTimeout(15, TimeUnit.MINUTES).writeTimeout(15, TimeUnit.MINUTES).addInterceptor(mInterceptor).build()

    //Call Webservice - LIVE / LOCAL
    fun getService(): WebserviceInterface {
        return client.create(WebserviceInterface::class.java)
    }

    private val client: Retrofit
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl("https://jsonplaceholder.typicode.com/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
            }
            return this.retrofit!!
        }


    //Get Base URL from Login Screen
    fun changeApiBaseUrl(newApiBaseUrl: String) {
        retrofit = Retrofit.Builder().baseUrl(newApiBaseUrl).client(okHttpClient).addConverterFactory(GsonConverterFactory.create()).build()
    }
}