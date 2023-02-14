package com.example.cricketoons.util

import android.content.Context
import android.net.ConnectivityManager

class Constants {
    companion object {
        const val BASE_URL = "https://cricket.sportmonks.com/api/v2.0/"
        const val API_KEY="ZX3EzsbPwkWCnMd7lX16FylS8OrGwHASkLXjUEX5mJep90cTuU95y4HK4N8Z"
        const val NUM_HOME_TABS = 2



        fun checkConnectivity(context: Context):Boolean{
            val connectivityManager= context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }
    }
}