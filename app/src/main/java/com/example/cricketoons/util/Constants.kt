package com.example.cricketoons.util

import android.content.Context
import android.net.ConnectivityManager
import java.text.SimpleDateFormat
import java.util.*

class Constants {
    companion object {
        const val BASE_URL = "https://cricket.sportmonks.com/api/v2.0/"
        const val API_KEY="LSby1fTszwqffsTqFROKl0VUF3Jq5EkQrNalJPR9WLMtolgqJRzlTlm45sMl"
        //ZX3EzsbPwkWCnMd7lX16FylS8OrGwHASkLXjUEX5mJep90cTuU95y4HK4N8Z
        const val NUM_HOME_TABS = 2
        const val MATCH_DAY="Match Day"



        fun checkConnectivity(context: Context):Boolean{
            val connectivityManager= context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val networkInfo=connectivityManager.activeNetworkInfo
            return  networkInfo!=null && networkInfo.isConnected
        }

        fun getCurrentDate(): String {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return sdf.format(Calendar.getInstance().time)
        }
    }
}