package com.example.cricketoons.network

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.cricketoons.model.fixtures.Fixture
import com.example.cricketoons.model.fixtures.FixtureData
import com.example.cricketoons.util.Constants.Companion.API_KEY
import retrofit2.http.GET

interface CricketApiService {

    @GET("fixtures?api_token=${API_KEY}")
    suspend fun getFixture(): Fixture

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllFixtures(fixtureData: List<FixtureData>)
}