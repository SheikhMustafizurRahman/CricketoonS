package com.example.cricketoons.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketoons.viewmodel.ViewModel

class TeamsAdapter(val context: Context, val viewModel: ViewModel) : RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {

    class TeamViewHolder(view: View):RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}