package com.example.cricketoons.repository

import androidx.lifecycle.LiveData
import com.example.cricketoons.model.fixtures.FixtureData
import com.example.cricketoons.model.room.CricDao
import com.example.cricketoons.network.RetrofitInstance.Companion.CrickMonkAPI

class CricRepo(private val cricDao: CricDao) {

    val readFixtureData: LiveData<List<FixtureData>> = cricDao.getFixture()

    suspend fun readUpcoming():List<FixtureData> = CrickMonkAPI.getUpcomingMatch().data

    suspend fun getAllFixture(): List<FixtureData> {
        return CrickMonkAPI.getFixture().data
    }



    suspend fun getAllUpcomingMatches():List<FixtureData>{
        return CrickMonkAPI.getUpcomingMatch().data
    }

    suspend fun insertFixture(fixtureData: List<FixtureData>){
        cricDao.insertFixture(fixtureData)
    }
}