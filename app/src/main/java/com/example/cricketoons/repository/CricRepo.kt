package com.example.cricketoons.repository

import androidx.lifecycle.LiveData
import com.example.cricketoons.model.apiSpecificTeamwithSquad.Squad
import com.example.cricketoons.model.fixtureWithTeam.FixtureDataWteam
import com.example.cricketoons.model.room.CricDao
import com.example.cricketoons.model.roomCountry.Country
import com.example.cricketoons.model.roomFixtures.FixtureData
import com.example.cricketoons.model.roomLeague.League
import com.example.cricketoons.model.roomSeason.Season
import com.example.cricketoons.model.roomStages.Stage
import com.example.cricketoons.model.roomTeams.TeamData
import com.example.cricketoons.model.roomVenue.Venue
import com.example.cricketoons.network.RetrofitInstance.Companion.CrickMonkAPI

class CricRepo(private val cricDao: CricDao) {

    val readFixtureData: LiveData<List<FixtureData>> = cricDao.getFixture()
    val readTeamData: LiveData<List<TeamData>> = cricDao.getTeamsFromRoom()
    val readSquadData: LiveData<List<Squad>> = cricDao.readSquadPlayersFromRoom()


    suspend fun readUpcoming(): List<FixtureDataWteam> = CrickMonkAPI.getUpcomingMatch().data

    suspend fun getAllFixture(): List<FixtureData> {
        return CrickMonkAPI.getFixture().data
    }

    suspend fun getTeams(): List<TeamData> {
        return CrickMonkAPI.getTeamFromAPI().data
    }

    suspend fun fetchRecentSquadFromAPI(teamId: Int): List<Squad>? {
        return CrickMonkAPI.fetchRecentSquadFromAPI(teamId).data.squad
    }
    suspend fun fetchCountrySeasonStageLeagueVenueFromAPI() {
        insertSeasonFromAPI(CrickMonkAPI.fetchSeasonFromAPI().data)
        insertStageFromAPI(CrickMonkAPI.fetchStageFromAPI().data)
        insertLeagueFromAPI(CrickMonkAPI.fetchLeagueFromAPI().data)
        insertVenueFromAPI(CrickMonkAPI.fetchVenueFromAPI().data)
        insertCounty(CrickMonkAPI.fetchCountryFromAPI().data)

    }

    private suspend fun insertVenueFromAPI(data: List<Venue>) {
        cricDao.insertVenue(data)
    }

    private suspend fun insertLeagueFromAPI(leagueList: List<League>) {
        cricDao.insertLeague(leagueList)
    }

    private suspend fun insertStageFromAPI(stageList: List<Stage>) {
        cricDao.insertStages(stageList)
    }

    private suspend fun insertSeasonFromAPI(seasonList: List<Season>) {
        cricDao.insertSeason(seasonList)
    }

    private suspend fun insertCounty(countryList: List<Country>) {
        cricDao.insertCountry(countryList)
    }

    suspend fun insertFixture(fixtureData: List<FixtureData>) {
        cricDao.insertFixture(fixtureData)
    }

    suspend fun insertTeamInRoom(teamData: List<TeamData>) {
        cricDao.insertTeam(teamData)
    }

    suspend fun insertSquadInRoom(squadList: List<Squad>?) {
        cricDao.insertSquad(squadList)
    }

    suspend fun readCountryNameFromRoom(country_id: Int): String {
        return cricDao.readCountryNameByID(country_id)
    }

    suspend fun readSquadByCountryID(teamId: Int): List<Squad>? {
        return CrickMonkAPI.fetchRecentSquadFromAPI(teamId).data.squad
    }

}