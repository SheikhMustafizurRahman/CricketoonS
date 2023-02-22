package com.example.cricketoons

import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cricketoons.databinding.ActivityMainBinding
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
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

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_host) as NavHostFragment
        navController = navHostFragment.navController
        val bottomNavigationView = binding.bottomNavigationView
        bottomNavigationView.setupWithNavController(navController)

// Set custom item background
        bottomNavigationView.itemBackground =
            ContextCompat.getDrawable(this, R.drawable.bottom_nav_item_background)

// Set item text and icon colors programmatically
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            // Change text color
            val states = arrayOf(
                intArrayOf(android.R.attr.state_checked),
                intArrayOf(-android.R.attr.state_checked)
            )
            val colors = intArrayOf(
                ContextCompat.getColor(this, R.color.colorOnSecondary),
                ContextCompat.getColor(this, R.color.colorOnSurface)
            )
            val textColors = ColorStateList(states, colors)
            bottomNavigationView.itemTextColor = textColors

            // Change icon color
            val iconColors = ColorStateList(states, colors)
            bottomNavigationView.itemIconTintList = iconColors

            // Return true to update the selected item
            true
        }


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
                R.id.ranking -> {
                    findNavController(binding.fragmentHost).navigate(R.id.rankingFragment)
                }
            }
            true
        }
        //sharedPrefwork
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        val isNewInstall = sharedPreferences.getBoolean("isNewInstall", true)
        Log.d(TAG, "onCreate: $isNewInstall")

        if (isNewInstall) {
            val editor = sharedPreferences.edit()
            editor.putBoolean("isNewInstall", false)
            editor.apply()
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.getTeamsFromAPIStoreInRoom()
                viewModel.insertCountryLeagueSeasonVenueStageFromAPIT0Room()
            }
            Log.d(TAG, "onCreate: called")
        }
    }

}