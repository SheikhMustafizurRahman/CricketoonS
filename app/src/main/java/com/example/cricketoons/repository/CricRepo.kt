package com.example.cricketoons.repository

import androidx.lifecycle.LiveData
import com.example.cricketoons.model.fixtures.FixtureData
import com.example.cricketoons.model.room.CricDao

class CricRepo(private val cricDao: CricDao) {

    val readFixtureData:LiveData<List<FixtureData>> = cricDao.getFixture()

    suspend fun insertFixture(fixtureData: List<FixtureData>){
        cricDao.insertFixture(fixtureData)
    }
}