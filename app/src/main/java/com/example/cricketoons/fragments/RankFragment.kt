package com.example.cricketoons.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketoons.adapter.RankingAdapter
import com.example.cricketoons.databinding.FragmentRankBinding
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "RankFragment"

class RankFragment(val type: String) : Fragment() {
    private var _binding: FragmentRankBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()
    constructor() : this("T20I")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRankBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            binding.teamsRv.layoutManager = LinearLayoutManager(requireContext())
            binding.teamsRv.setHasFixedSize(true)
            CoroutineScope(Dispatchers.IO).launch {
                val rankingData = viewModel.fetchRankingFromAPI(type)
                val genderToFilter = "men" // specify the gender to filter by
                val filtered = rankingData.filter { it.gender == genderToFilter }
                val filteredTeams = filtered.flatMap { it.team }
                withContext(Dispatchers.Main) {
                    val adapter = RankingAdapter(requireContext(), viewModel)
                    Log.d(TAG, "onViewCreated: $filteredTeams")
                    adapter.setDataset(filteredTeams)
                    binding.teamsRv.adapter = adapter
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "onViewCreated: ${e.message}")
        }

    }

}
