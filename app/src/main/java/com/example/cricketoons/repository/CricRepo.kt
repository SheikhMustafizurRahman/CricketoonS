package com.example.cricketoons.repository

import androidx.lifecycle.LiveData
import com.example.cricketoons.model.roomFixtures.FixtureData
import com.example.cricketoons.model.roomTeams.TeamData
import com.example.cricketoons.model.fixtureWithTeam.FixtureDataWteam
import com.example.cricketoons.model.room.CricDao
import com.example.cricketoons.network.RetrofitInstance.Companion.CrickMonkAPI

class CricRepo(private val cricDao: CricDao) {

    val readFixtureData: LiveData<List<FixtureData>> = cricDao.getFixture()

    suspend fun readUpcoming():List<FixtureDataWteam> = CrickMonkAPI.getUpcomingMatch().data

    suspend fun getAllFixture(): List<FixtureData> {
        return CrickMonkAPI.getFixture().data
    }

    suspend fun getTeams():List<TeamData>{
        return CrickMonkAPI.getTeamFromAPI().data
    }

    suspend fun insertFixture(fixtureData: List<FixtureData>){
        cricDao.insertFixture(fixtureData)
    }

    suspend fun insertTeamInRoom(teamData: List<TeamData>){
        cricDao.insertTeam(teamData)
    }
}