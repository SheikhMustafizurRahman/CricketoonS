/*
package com.example.cricketoons.util

import android.app.Application
import com.example.cricketoon.database.CricketDB
import com.example.cricketoon.repository.CricketRepository

class MyApplication : Application() {

    companion object {
        lateinit var cricketRepository: CricketRepository
            private set
    }

    override fun onCreate() {
        super.onCreate()
        val cricketDB = CricketDB.getDatabase(applicationContext)
        val dao = cricketDB.cricketDao()
        cricketRepository =CricketRepository(dao,cricketDB)

    }
}*/
