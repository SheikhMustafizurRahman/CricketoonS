package com.example.cricketoons.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cricketoons.adapter.ViewPagerAdapter
import com.example.cricketoons.databinding.FragmentHomeBinding
import com.example.cricketoons.util.Constants.Companion.checkConnectivity
import com.example.cricketoons.viewmodel.ViewModel
import com.google.android.material.tabs.TabLayoutMediator


class HomeFragment : Fragment() {

    val TabArray = arrayOf(
        "Live", "Recent")

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (checkConnectivity(requireContext())) {
            Toast.makeText(requireContext(), "NetworkAvaiable", Toast.LENGTH_SHORT).show()
        } else Log.d("HomeFragment", "Network not available")
        val viewPager = binding.homeViewPager
        val tabLayout = binding.tabMode
        val adapter = ViewPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = adapter

//        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
//            tab.text = TabArray[0]
//        }.attach()
        TabLayoutMediator(tabLayout, viewPager
        ) { tab, position ->
            //Configure tabs..
            when (position) {
                0 -> {
                    tab.text = "tab 1"
                }
                1 -> {
                    tab.text = "tab 2"
                }
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}