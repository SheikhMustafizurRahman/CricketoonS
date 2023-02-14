package com.example.cricketoons.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketoons.adapter.LiveMatchAdapter
import com.example.cricketoons.adapter.UpcomingMatchAdapter
import com.example.cricketoons.databinding.FragmentLiveAndUpcomingBinding
import com.example.cricketoons.util.Constants.Companion.checkConnectivity
import com.example.cricketoons.viewmodel.ViewModel

class LiveAndUpcomingFragment : Fragment() {
    private var _binding: FragmentLiveAndUpcomingBinding? = null
    val binding get() = _binding!!
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

        try {
            binding.swipeRefreshLayout.setOnRefreshListener {
                if (checkConnectivity(requireContext())) {
                    Log.d("TAG", "onViewCreated: NetWorkAvailable")
                }
                binding.swipeRefreshLayout.isRefreshing = false
            }

            Log.d("LiveAndUpcomingFragments", "Currently She said Yes")
            binding.liveMatchRv.layoutManager = LinearLayoutManager(requireContext())
            binding.liveMatchRv.setHasFixedSize(true)
            binding.upcomingMatchRv.layoutManager = LinearLayoutManager(requireContext())
            binding.upcomingMatchRv.setHasFixedSize(true)
            //displayLive()
            displayUpcoming()
            Log.d("Yahoo", "fdsfsd: ")
        } catch (e: Exception) {
            Log.d("LiveUpdate", "onViewCreated: ${e.message}")
        }
    }

    private fun displayUpcoming() {
/*        var liveDataList: MutableLiveData<List<FixtureData>>
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            val list = viewModel.readUpcoming_matches()
            liveDataList = MutableLiveData<List<FixtureData>>().apply {
                value = list
            }
        }*/
        viewModel.fixtureLive.observe(viewLifecycleOwner){
            val recyclerViewState = binding.upcomingMatchRv.layoutManager?.onSaveInstanceState()
            // Restore state
            binding.upcomingMatchRv.layoutManager?.onRestoreInstanceState(recyclerViewState)
//            binding.upcomingMatchRv.layoutManager=LinearLayoutManager(requireContext())
            val adapter = UpcomingMatchAdapter(requireContext(), viewModel)
            adapter.setDataset(it)
            Log.d("TAG2", "DisplayItem:$adapter ")
            binding.upcomingMatchRv.adapter = adapter
            Log.d("TAG3", "DisplayItem:$adapter ")
        }
    }

    private fun displayLive() {
        val recyclerViewState = binding.liveMatchRv.layoutManager?.onSaveInstanceState()
        // Restore state
        binding.liveMatchRv.layoutManager?.onRestoreInstanceState(recyclerViewState)
        val adapter = LiveMatchAdapter(requireContext(), viewModel)
        Log.d("TAGx", "DisplayItem: ")
        binding.liveMatchRv.adapter = adapter
    }
}