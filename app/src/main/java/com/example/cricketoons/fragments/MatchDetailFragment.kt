package com.example.cricketoons.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cricketoons.R
import com.example.cricketoons.adapter.MatchDetailViewPagerAdapter
import com.example.cricketoons.databinding.FragmentMatchDetailBinding
import com.example.cricketoons.viewmodel.ViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MatchDetailFragment"

class MatchDetailFragment : Fragment() {
    private var _binding: FragmentMatchDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()
    private val args: MatchDetailFragmentArgs by navArgs()

    val TabTxtArray = arrayOf(
        "INFO", "SCORECARDS", "SQUADS"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMatchDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fixture=args.fixture
        try {
            viewModel.viewModelScope.launch(Dispatchers.IO) {
                binding.team1Score.text =
                    viewModel.getTeamNameFromRoom(fixture.localteam_id!!)
                binding.team2Score.text =
                    viewModel.getTeamNameFromRoom(fixture.visitorteam_id!!)
                val localTeamLogo = viewModel.getTeamLogoFromRoom(fixture.localteam_id!!)
                val visitorTeamLogo = viewModel.getTeamLogoFromRoom(fixture.visitorteam_id!!)

                withContext(Dispatchers.Main) {
                    Glide.with(requireContext()).load(localTeamLogo).error(R.drawable.bdflag)
                        .into(binding.teamFlagH)
                    Glide.with(requireContext()).load(visitorTeamLogo).error(R.drawable.japanflag)
                        .into(binding.teamflagA)
                }
            }

        } catch (e: Exception) {
            Log.e(TAG, "onViewCreated: ${e.message}")
        }
        val viewPager = binding.detailViewPager
        val tabLayout = binding.tabModeMD
        val adapter = MatchDetailViewPagerAdapter(childFragmentManager, lifecycle,fixture)
        viewPager.adapter = adapter
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = TabTxtArray[position]
        }.attach()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}