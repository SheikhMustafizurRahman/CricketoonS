package com.example.cricketoons.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cricketoons.model.database.CricketDB
import com.example.cricketoons.model.fixtures.FixtureData
import com.example.cricketoons.repository.CricRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "ViewModel"

class ViewModel(application: Application) : AndroidViewModel(application) {

    private val _fixtureLive = MutableLiveData<List<FixtureData>>()
    private val fixtureDataList: LiveData<List<FixtureData>>
    val fixtureLive: LiveData<List<FixtureData>> = _fixtureLive
    val repository: CricRepo

    init {
        val dao = CricketDB.getDatabase(application).cricketDao()
        repository = CricRepo(dao)
        fixtureDataList = repository.readFixtureData
/*        val list =
        val liveDataList = MutableLiveData<List<FixtureData>>().apply {
            value = list
        }*/
        getFixtureFromAPI()
    }

    fun getFixtureFromAPI() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _fixtureLive.postValue(repository.getAllFixture())
                Log.d("list", fixtureLive.value?.size.toString())
                fixtureLive.value?.let {
                    Log.d(TAG, "getFixtureFromAPI: called")
                    repository.insertFixture(it)
                }
            } catch (e: Exception) {
                Log.d(TAG, "error found: $e")
            }
        }
    }
    suspend fun readUpcoming_matches():List<FixtureData> = repository.readUpcoming()

/*    fun getLiveFromAPI(): LiveData<List<FixtureData>> {
        try {
            _fixtureLive.postValue(CrickMonkAPI.getLiveScore().data)
            Log.d("list", fixtureLive.value?.size.toString())
        } catch (e: Exception) {
            Log.d(TAG, "getLiveFromAPI: $e")
        }
        return fixtureLive
    }*/

}