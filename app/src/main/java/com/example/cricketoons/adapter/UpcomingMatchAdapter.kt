package com.example.cricketoons.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketoons.R
import com.example.cricketoons.model.fixtures.FixtureData
import com.example.cricketoons.viewmodel.ViewModel

class UpcomingMatchAdapter(val context: Context, val viewModel: ViewModel) : RecyclerView.Adapter<UpcomingMatchAdapter.UpcomingViewHolder>() {

    private var upcoming= emptyList<FixtureData>()

    class UpcomingViewHolder(private val view: View):RecyclerView.ViewHolder(view) {
        val teamOne: TextView =view.findViewById(R.id.team1Score)
        val teamTwo: TextView =view.findViewById(R.id.team2Score)
        val teamOneFlag: ImageView =view.findViewById(R.id.team1flag)
        val teamTwoFlag: ImageView =view.findViewById(R.id.team2flag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingViewHolder {
        val layout=LayoutInflater.from(parent.context).inflate(R.layout.upcoming_card_view,parent,false)
        Log.d("Upcoming Match holder", "onCreateViewHolder: ")
        return UpcomingViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return upcoming.size
    }

    override fun onBindViewHolder(holder: UpcomingViewHolder, position: Int) {
        val upcomingMatch= upcoming[position]
        holder.teamOne.text=upcomingMatch.status
        holder.teamTwo.text=upcomingMatch.note
    }

    fun setDataset(it: List<FixtureData>) {
        upcoming=it
    }

}
