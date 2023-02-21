package com.example.cricketoons.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketoons.adapter.LiveMatchAdapter
import com.example.cricketoons.adapter.UpcomingMatchAdapter
import com.example.cricketoons.databinding.FragmentLiveAndUpcomingBinding
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.model.fixtureWithTeam.FixtureDataWteam
import com.example.cricketoons.util.Constants.Companion.checkConnectivity
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LiveAndUpcomingFragment : Fragment() {
    private var _binding: FragmentLiveAndUpcomingBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLiveAndUpcomingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        GlobalScope.launch(Dispatchers.IO) {
            try {
                binding.swipeRefreshLayout.setOnRefreshListener {
                    if (checkConnectivity(requireContext())) {
                        Log.d("TAG", "onViewCreated: NetWorkAvailable")
                    }
                    binding.swipeRefreshLayout.isRefreshing = false
                }
                binding.liveMatchRv.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
                binding.liveMatchRv.setHasFixedSize(true)
                binding.upcomingMatchRv.layoutManager = LinearLayoutManager(requireContext())
                binding.upcomingMatchRv.setHasFixedSize(true)
                displayLive()
                displayUpcoming()
            } catch (e: Exception) {
                Log.d("LiveUpdateFragment", "onViewCreated: ${e.message}")
            }
        }
    }

    private suspend fun displayUpcoming() {
        val list = viewModel.readUpcomingMatches()
        withContext(Dispatchers.Main) {
            val liveDataList: MutableLiveData<List<FixtureDataWteam>> =
                MutableLiveData<List<FixtureDataWteam>>().apply {
                    value = list
                }
            liveDataList.observe(viewLifecycleOwner) {
                val recyclerViewState = binding.upcomingMatchRv.layoutManager?.onSaveInstanceState()
                // Restore state
                binding.upcomingMatchRv.layoutManager?.onRestoreInstanceState(recyclerViewState)
                val adapter = UpcomingMatchAdapter(requireContext(), viewModel)
                adapter.setDataset(it)
                binding.upcomingMatchRv.adapter = adapter
            }
        }
    }

    private suspend fun displayLive() {
        val fixture = viewModel.getLive()
        withContext(Dispatchers.Main) {
            val liveDataList: MutableLiveData<List<Fixture>> =
                MutableLiveData<List<Fixture>>().apply {
                    value = fixture
                }
            liveDataList.observe(viewLifecycleOwner) {
                val recyclerViewState = binding.liveMatchRv.layoutManager?.onSaveInstanceState()
                // Restore state
                binding.liveMatchRv.layoutManager?.onRestoreInstanceState(recyclerViewState)
                val adapter = LiveMatchAdapter(requireContext(), viewModel)
                Log.d("TAGx", "DisplayItem: ")
                adapter.setDataset(it)
                binding.liveMatchRv.visibility = if (adapter.itemCount == 0) View.GONE else View.VISIBLE
                binding.liveMatchRv.adapter = adapter
            }
        }
    }
}