package com.example.cricketoons.network

import com.example.cricketoons.model.fixtures.Fixture
import com.example.cricketoons.util.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface CricketApiService {

    @GET("fixtures?api_token=${API_KEY}")
    suspend fun getFixture(): Fixture


    @GET("livescores?api_token=${API_KEY}")
    fun getLiveScore(): Fixture

    @GET("fixtures")
    suspend fun getUpcomingMatch(
        @Query("filter[starts_between]") duration: String = "2023-02-14,2023-03-19",
        @Query("include=")params:String="localteam,visitorteam",
        @Query("api_token") api_token: String = API_KEY
    ): Fixture

}