package com.example.cricketoons

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cricketoons.databinding.ActivityMainBinding
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: ViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)

        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    findNavController(binding.fragmentHost).navigate(R.id.homeFragment)
                }
                R.id.team -> {
                    findNavController(binding.fragmentHost).navigate(R.id.teamFragment)
                }
                R.id.searchplayer -> {
                    findNavController(binding.fragmentHost).navigate(R.id.searchFragment)
                }
            }
            true
        }
        //sharedPrefwork
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val isNewInstall = sharedPreferences.getBoolean("isNewInstall", true)
        Log.d(TAG, "onCreate: $isNewInstall")

        if(isNewInstall){
            val editor = sharedPreferences.edit()
            editor.putBoolean("isNewInstall", false)
            editor.apply()
            viewModel.getTeamsFromAPIStoreInRoom()
            viewModel.viewModelScope.launch(Dispatchers.IO){
                viewModel.insertCountryLeagueSeasonVenueStageFromAPIT0Room()
            }
            Log.d(TAG, "onCreate: called")
        }
    }

}