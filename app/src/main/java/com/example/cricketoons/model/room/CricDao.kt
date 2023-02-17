package com.example.cricketoons.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cricketoons.model.apiSpecificTeamwithSquad.Squad
import com.example.cricketoons.model.roomCountry.Country
import com.example.cricketoons.model.roomFixtures.FixtureData
import com.example.cricketoons.model.roomLeague.League
import com.example.cricketoons.model.roomSeason.Season
import com.example.cricketoons.model.roomStages.Stage
import com.example.cricketoons.model.roomTeams.TeamData
import com.example.cricketoons.model.roomVenue.Venue

@Dao
interface CricDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFixture(fixtureData: List<FixtureData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(teamData: List<TeamData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSquad(squad: List<Squad>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(countryList: List<Country>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLeague(leagueList: List<League>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSeason(seasonList: List<Season>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVenue(venueList: List<Venue>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStages(stageList: List<Stage>)

    @Query("SELECT * FROM fixtureTable")
    fun getFixture():LiveData<List<FixtureData>>

    @Query("select * from fixtureTable where status='Finished' order by starting_at desc limit 10")
    fun readUpcoming():LiveData<List<FixtureData>>

    @Query("select * from teams where national_team= true order by name")
    fun getTeamsFromRoom():LiveData<List<TeamData>>

    @Query("select * from squadTable order by country_id")
    fun readSquadPlayersFromRoom():LiveData<List<Squad>>

    @Query("select name from country where id=:country_id")
    suspend fun readCountryNameByID(country_id:Int):String

    @Query("select * from squadTable where id=:country_id")
    fun readSquadByCountryID(country_id:Int):LiveData<List<Squad>>
}