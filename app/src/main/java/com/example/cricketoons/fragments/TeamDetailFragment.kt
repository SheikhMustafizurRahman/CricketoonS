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
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.cricketoons.R
import com.example.cricketoons.adapter.PlayerSearchAdapter
import com.example.cricketoons.databinding.FragmentTeamDetailsBinding
import com.example.cricketoons.model.apiSpecificTeamwithSquad.Squad
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamDetailFragment() : Fragment() {

    private var _binding: FragmentTeamDetailsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()
    val args: TeamDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTeamDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Glide.with(requireContext()).load(args.teamLogo).error(R.drawable.no_pictures)
            .into(binding.teamflag)
            binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
            binding.recyclerView.setHasFixedSize(true)
            getTeamFromAPI()
    }

    private fun getTeamFromAPI() {
        val squad = MutableLiveData<List<Squad>>()
        viewModel.viewModelScope.launch(Dispatchers.IO) {
            try {
                squad.postValue(viewModel.readSquadByCountryID(args.teamId))
                val regularList = squad.value.orEmpty()
                val recyclerViewState = binding.recyclerView.layoutManager?.onSaveInstanceState()
                binding.recyclerView.layoutManager?.onRestoreInstanceState(recyclerViewState)
                val adapter = PlayerSearchAdapter(requireContext(), viewModel)
                adapter.setDataset(regularList)
                binding.recyclerView.adapter = adapter
            } catch (e: Exception) {
                Log.e("TAG", "getTeamFromAPI: ${e.message}", )
            }
        }
    }

}