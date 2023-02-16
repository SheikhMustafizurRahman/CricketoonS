package com.example.cricketoons.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricketoons.R
import com.example.cricketoons.model.roomTeams.TeamData
import com.example.cricketoons.viewmodel.ViewModel

class TeamsAdapter(val context: Context, val viewModel: ViewModel) : RecyclerView.Adapter<TeamsAdapter.TeamViewHolder>() {

    private var teams= emptyList<TeamData>()
    class TeamViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val teamNameTV:TextView=view.findViewById(R.id.teamName)
        val teamCodeTV:TextView=view.findViewById(R.id.teamCode)
        val teamLogo:ImageView=view.findViewById(R.id.teamLogo)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val layout= LayoutInflater.from(parent.context).inflate(R.layout.team_card,parent,false)
        return TeamViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team=teams[position]
        holder.teamNameTV.text=team.name
        holder.teamCodeTV.text=team.code
        Glide.with(context).load(team.image_path).error(R.drawable.no_pictures).into(holder.teamLogo)
        viewModel.getSquadFromAPIStoreInRoom(team.id)
    }

    fun setDataset(it: List<TeamData>) {
        teams=it
    }
}