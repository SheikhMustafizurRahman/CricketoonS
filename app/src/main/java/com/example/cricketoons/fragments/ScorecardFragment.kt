package com.example.cricketoons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketoons.R
import com.example.cricketoons.adapter.BattingScorecardAdapter
import com.example.cricketoons.adapter.BowlingScorecardAdapter
import com.example.cricketoons.databinding.FragmentMatchDetailScorecardBinding
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.viewmodel.ViewModel

class ScorecardFragment(val fixture: Fixture) : Fragment() {
    private var _binding: FragmentMatchDetailScorecardBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    private var localTeam = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMatchDetailScorecardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        selectTeamTab()
        binding.bowlingScoreRV.layoutManager = LinearLayoutManager(requireContext())
        binding.bowlingScoreRV.setHasFixedSize(true)
        binding.battingScoreRV.layoutManager = LinearLayoutManager(requireContext())
        binding.battingScoreRV.setHasFixedSize(true)

        displayScore()
        binding.teamOne.setOnClickListener {
            localTeam = true
            selectTeamTab()
            displayScore()
        }
        binding.teamTwo.setOnClickListener {
            localTeam = false
            selectTeamTab()
            displayScore()
        }

    }

    private fun displayScore() {
        val batting = fixture.batting
        val adapter = BattingScorecardAdapter(requireContext(), viewModel, localTeam)

        adapter.setDataset(batting)
        val bowling = fixture.bowling
        val bowlingAdapter= BowlingScorecardAdapter(requireContext(),viewModel,localTeam)
        bowlingAdapter.setDataset(bowling)
        binding.battingScoreRV.adapter = adapter
        binding.bowlingScoreRV.adapter = bowlingAdapter
    }

    private fun selectTeamTab() {
        if (localTeam) {
            binding.teamOne.isSelected = true
            binding.teamOne.setBackgroundResource(R.color.colorOnSelect)

            binding.teamTwo.isSelected = false
            binding.teamTwo.setBackgroundResource(R.color.colorBackground)
        } else {
            binding.teamTwo.isSelected = true
            binding.teamTwo.setBackgroundResource(R.color.colorOnSelect)

            binding.teamOne.isSelected = false
            binding.teamOne.setBackgroundResource(R.color.colorBackground)
        }
    }


}
