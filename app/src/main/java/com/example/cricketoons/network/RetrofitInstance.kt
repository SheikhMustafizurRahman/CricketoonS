package com.example.cricketoons.network

import com.example.cricketoons.util.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitInstance {

    companion object {
        private val retrofit by lazy {
            val moshi = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            val client = OkHttpClient.Builder().addInterceptor(logging).build()
            Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(client)
                .baseUrl(Constants.BASE_URL)
                .build()
        }
        val CrickMonkAPI: CricketApiService by lazy {
            retrofit.create(CricketApiService::class.java)
        }
    }

}