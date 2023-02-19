package com.example.cricketoons.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cricketoons.databinding.FragmentMatchDetailInfoBinding
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.viewmodel.ViewModel

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.seriesName.text=fixture.note
    }
}
