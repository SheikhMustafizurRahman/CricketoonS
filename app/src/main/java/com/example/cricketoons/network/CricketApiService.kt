package com.example.cricketoons.network

import com.example.cricketoons.model.apiSpecificTeamwithSquad.TeamSquadFromAPI
import com.example.cricketoons.model.fixtureWithTeam.FixturewithTeam
import com.example.cricketoons.model.roomFixtures.Fixture
import com.example.cricketoons.model.roomTeams.TeamSquad
import com.example.cricketoons.util.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CricketApiService {

    @GET("fixtures?api_token=${API_KEY}")
    suspend fun getFixture(): Fixture

    @GET("livescores?api_token=${API_KEY}")
    fun getLiveScore(): Fixture

    @GET("fixtures")
    suspend fun getUpcomingMatch(
        @Query("filter[starts_between]") duration: String = "2023-02-15,2023-03-18",
        @Query("include") params: String = "localteam,visitorteam",
        @Query("api_token") api_token: String = API_KEY
    ): FixturewithTeam

    @GET("teams?api_token=${API_KEY}")
    suspend fun getTeamFromAPI(): TeamSquad

    @GET("teams/{teamId}/squad/23")
    suspend fun fetchRecentSquadFromAPI(
        @Path("teamId") teamId: Int,
        @Query("api_token") api_token: String = API_KEY
    ): TeamSquadFromAPI


}