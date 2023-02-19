package com.example.cricketoons.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cricketoons.databinding.FragmentMatchDetailInfoBinding
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.util.Constants
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MatchInfoFragment(val fixture: Fixture) : Fragment() {
    private var _binding: FragmentMatchDetailInfoBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMatchDetailInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.seriesName.text=fixture.note
        binding.matchdate.text= Constants.convertDateTimeToDateString(fixture.starting_at!!)
        var toss:String=""
        GlobalScope.launch(Dispatchers.IO) {
             toss=viewModel.getTeamNameFromRoom(fixture.toss_won_team_id!!)
            binding.toss.text="Toss won by $toss and chose to bat first"
        }

    }
}
