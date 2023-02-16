package com.example.cricketoons.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cricketoons.database.CricketDB
import com.example.cricketoons.model.apiSpecificTeamwithSquad.Squad
import com.example.cricketoons.model.fixtureWithTeam.FixtureDataWteam
import com.example.cricketoons.model.roomFixtures.FixtureData
import com.example.cricketoons.model.roomTeams.TeamData
import com.example.cricketoons.repository.CricRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "ViewModel"

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val _fixtureLive = MutableLiveData<List<FixtureData>>()
    private val _teams=MutableLiveData<List<TeamData>>()
    private val fixtureDataList: LiveData<List<FixtureData>>
    val fixtureLive: LiveData<List<FixtureData>> = _fixtureLive
    val teams:LiveData<List<TeamData>> =_teams
    val repository: CricRepo

    init {
        val dao = CricketDB.getDatabase(application).cricketDao()
        repository = CricRepo(dao)
        fixtureDataList = repository.readFixtureData
    }

    fun getFixtureFromAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _fixtureLive.postValue(repository.getAllFixture())
                fixtureLive.value?.let {
                    Log.d(TAG, "getFixtureFromAPI: called")
                    repository.insertFixture(it)
                }
            } catch (e: Exception) {
                Log.d(TAG, "error found: $e")
            }
        }
    }
    suspend fun readUpcoming_matches():List<FixtureDataWteam> = repository.readUpcoming()

/*    fun getLiveFromAPI(): LiveData<List<FixtureData>> {
        try {
            _fixtureLive.postValue(CrickMonkAPI.getLiveScore().data)
            Log.d("list", fixtureLive.value?.size.toString())
        } catch (e: Exception) {
            Log.d(TAG, "getLiveFromAPI: $e")
        }
        return fixtureLive
    }*/
    fun getTeamsFromAPIStoreInRoom(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _teams.postValue(repository.getTeams())
                teams.value?.let{
                    Log.d(TAG, "Storing Data in Room: called")
                    repository.insertTeamInRoom(it)
                }
            }catch (e:Exception){
                Log.e(TAG, "getTeamsFromAPIandStoreInRoom:${e.message}", )
            }
        }
    }
    fun getTeamsFromRoom():LiveData<List<TeamData>> =repository.readTeamData

    fun getALlSquadPlayerFromRoom():LiveData<List<Squad>> =repository.readSquadData

    fun getSquadFromAPIStoreInRoom(teamId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                //fetchind Squad data from api using the TeamID
                val currentSquad=repository.fetchRecentSquadFromAPI(teamId)
                repository.insertSquadInRoom(currentSquad)

            }catch (e:Exception){
                Log.e(TAG, "getTeamsFromRoom:${e.message}", )
            }
        }
    }

}