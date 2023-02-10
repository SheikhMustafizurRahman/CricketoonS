package com.example.cricketoons.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cricketoons.database.CricketDB
import com.example.cricketoons.model.fixtures.Fixture
import com.example.cricketoons.model.fixtures.FixtureData
import com.example.cricketoons.network.RetrofitInstance.Companion.CrickMonkAPI
import com.example.cricketoons.repository.CricRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

private const val TAG = "ViewModel"
class ViewModel(application: Application):AndroidViewModel(application) {

    private val _fixtureLive = MutableLiveData<List<FixtureData>>()
    val fixtureLive:LiveData<List<FixtureData>> = _fixtureLive
    private val fixtureDataList: LiveData<List<FixtureData>>
    val repository: CricRepo

    init {
        val dao=CricketDB.getDatabase(application).cricketDao()
        repository= CricRepo(dao)
        fixtureDataList=repository.readFixtureData

        getFixtureFromAPI()


    }

    fun getFixtureFromAPI(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _fixtureLive.postValue(CrickMonkAPI.getFixture().data)
                Log.d("list", fixtureLive.value?.size.toString())
                fixtureLive.value?.let {
                    Log.d(TAG, "getFixtureFromAPI: called")
                    repository.insertFixture(it) }
            }catch (e: Exception){
                Log.d(TAG, "getFixtureFromAPI: $e")
            }
        }
    }
}