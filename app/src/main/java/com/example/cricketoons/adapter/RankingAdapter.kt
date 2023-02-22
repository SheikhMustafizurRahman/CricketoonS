package com.example.cricketoons.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricketoons.R
import com.example.cricketoons.model.rankingAPI.Team
import com.example.cricketoons.viewmodel.ViewModel

class RankingAdapter(val context: Context,val viewModel: ViewModel) : RecyclerView.Adapter<RankingAdapter.ViewHolder>() {

    private var teams= emptyList<Team>()

    class ViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val teamNameTV: TextView =view.findViewById(R.id.teamName)
        val teamCodeTV: TextView =view.findViewById(R.id.teamCode)
        val teamLogo: ImageView =view.findViewById(R.id.teamLogo)
        val teamCard: CardView = view.findViewById(R.id.cardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout= LayoutInflater.from(context).inflate(R.layout.team_card,parent,false)
        return ViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val team=teams[position]
        holder.teamNameTV.text=team.name
        holder.teamCodeTV.text=team.code
        Glide.with(context).load(team.image_path).error(R.drawable.no_pictures).into(holder.teamLogo)
    }

    fun setDataset(filteredTeams: List<Team>) {
        teams=filteredTeams
    }

}
