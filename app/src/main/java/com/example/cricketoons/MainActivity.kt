package com.example.cricketoons

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.cricketoons.databinding.ActivityMainBinding
import com.example.cricketoons.repository.CricRepo
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModel= ViewModelProvider(this)[ViewModel::class.java]


    }
}