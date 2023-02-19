package com.example.cricketoons.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketoons.adapter.RecentMatchAdapter
import com.example.cricketoons.databinding.FragmentRecentBinding
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.util.Constants
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "RecentFragment"

class RecentFragment : Fragment() {
    private var _binding: FragmentRecentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            try {
                binding.recentRefresh.setOnRefreshListener {
                    if (Constants.checkConnectivity(requireContext())) {
                        Log.d("TAG", "onViewCreated: NetWorkAvailable")
                    }
                    binding.recentRefresh.isRefreshing = false
                }
                binding.recentMatchRv.layoutManager = LinearLayoutManager(requireContext())
                binding.recentMatchRv.setHasFixedSize(true)

                displayRecent()
            } catch (e: Exception) {
                Log.d(TAG, "onViewCreated: ${e.message}")
            }
        }


    }

    private suspend fun displayRecent() {
        val list = viewModel.readRecentMatches()
        withContext(Dispatchers.Main) {
            val liveDataList: MutableLiveData<List<Fixture>> =
                MutableLiveData<List<Fixture>>().apply {
                    value = list
                }

            liveDataList.observe(viewLifecycleOwner){
                val recyclerViewState=binding.recentMatchRv.layoutManager?.onSaveInstanceState()
                binding.recentMatchRv.layoutManager?.onRestoreInstanceState(recyclerViewState)

                val adapter= RecentMatchAdapter(requireContext(),viewModel)
                adapter.setDataset(it)
                binding.recentMatchRv.adapter=adapter
            }
        }
    }
}
