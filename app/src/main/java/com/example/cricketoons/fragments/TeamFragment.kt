package com.example.cricketoons.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cricketoons.adapter.TeamsAdapter
import com.example.cricketoons.databinding.FragmentTeamBinding
import com.example.cricketoons.viewmodel.ViewModel


class TeamFragment : Fragment() {
    private var _binding:FragmentTeamBinding?=null
    private val binding get()=_binding!!
    private val viewModel:ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
_binding= FragmentTeamBinding.inflate(inflater,container,false)
    return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
            try{
                viewModel.getTeamsFromAPIStoreInRoom()
                binding.teamsRv.layoutManager = LinearLayoutManager(requireContext())
                binding.teamsRv.setHasFixedSize(true)

                viewModel.getTeamsFromRoom().observe(viewLifecycleOwner){
                    val recyclerViewState=binding.teamsRv.layoutManager?.onSaveInstanceState()
                    binding.teamsRv.layoutManager?.onRestoreInstanceState(recyclerViewState)
                    val adapter= TeamsAdapter(requireContext(),viewModel)
                    adapter.setDataset(it)
                    binding.teamsRv.adapter=adapter
                }
            } catch (e: Exception) {
                Log.d("TeamFragment", "onViewCreated: ${e.message}")
            }
    }
}