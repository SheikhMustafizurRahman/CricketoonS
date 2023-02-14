package com.example.cricketoons.model.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cricketoons.model.fixtures.FixtureData

@Dao
interface CricDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFixture(fixtureData: List<FixtureData>)

    @Query("SELECT * FROM fixtureTable")
    fun getFixture():LiveData<List<FixtureData>>

    @Query("select * from fixtureTable where status='Finished' order by starting_at desc limit 10")
    fun readUpcoming():LiveData<List<FixtureData>>
}