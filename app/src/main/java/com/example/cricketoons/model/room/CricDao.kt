package com.example.cricketoons.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cricketoons.model.apiSpecificTeamwithSquad.Squad
import com.example.cricketoons.model.roomFixtures.FixtureData
import com.example.cricketoons.model.roomTeams.TeamData

@Dao
interface CricDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFixture(fixtureData: List<FixtureData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(teamData: List<TeamData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSquad(squad: List<Squad>?)

    @Query("SELECT * FROM fixtureTable")
    fun getFixture():LiveData<List<FixtureData>>

    @Query("select * from fixtureTable where status='Finished' order by starting_at desc limit 10")
    fun readUpcoming():LiveData<List<FixtureData>>

    @Query("select * from teams where national_team= true order by name")
    fun getTeamsFromRoom():LiveData<List<TeamData>>
}