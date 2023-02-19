package com.example.cricketoons.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cricketoons.R
import com.example.cricketoons.fragments.HomeFragmentDirections
import com.example.cricketoons.model.apiFixture.Fixture
import com.example.cricketoons.viewmodel.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAGR = "RecentMatchAdapter"

class RecentMatchAdapter(val context: Context, val viewModel: ViewModel) :
    RecyclerView.Adapter<RecentMatchAdapter.RecentViewHolder>() {

    private var recent = emptyList<Fixture>()

    class RecentViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val teamOne: TextView = view.findViewById(R.id.team1Score)
        val teamTwo: TextView = view.findViewById(R.id.team2Score)
        val teamOneFlag: ImageView = view.findViewById(R.id.team1flag)
        val teamTwoFlag: ImageView = view.findViewById(R.id.team2flag)
        val countdownTime: TextView = view.findViewById(R.id.countdown_timer)
        val matchCard:CardView= view.findViewById(R.id.recentCV)
    }

    fun setDataset(fixtures: List<Fixture>) {
        recent = fixtures
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.recent_card, parent, false)
        return RecentViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return recent.size
    }

    override fun onBindViewHolder(holder: RecentViewHolder, position: Int) {
        val recentMatch = recent[position]
        var localTeam =""
        var visitorTeam =""
        var localTeamLogo =""
        var visitorTeamLogo =""
        try {
            CoroutineScope(Dispatchers.Main).launch {
                withContext(Dispatchers.IO) {
                    if (recentMatch.localteam_id?.let { viewModel.checkIfTeamExistInRoom(it) }!! > 0) {
                        // Data exists in the database
                        localTeam =viewModel.getTeamNameFromRoom(recentMatch.localteam_id!!)
                        visitorTeam=viewModel.getTeamNameFromRoom(recentMatch.visitorteam_id!!)
                        localTeamLogo=viewModel.getTeamLogoFromRoom(recentMatch.localteam_id!!)
                        visitorTeamLogo=viewModel.getTeamLogoFromRoom(recentMatch.visitorteam_id!!)
                    } else {
                        // Data does not exist in the database
                        Log.e(TAGR, "onBindViewHolder: Data Not Exist")
                    }
                }
                // Update the UI views on the UI thread
                holder.teamOne.text = localTeam
                holder.teamTwo.text = visitorTeam
                Glide.with(context).load(localTeamLogo).error(R.drawable.bdflag)
                    .into(holder.teamOneFlag)
                Glide.with(context).load(visitorTeamLogo).error(R.drawable.japanflag)
                    .into(holder.teamTwoFlag)
            }
            holder.matchCard.setOnClickListener {
                val action=HomeFragmentDirections.actionHomeFragmentToMatchDetailFragment(recentMatch)
                Navigation.findNavController(holder.itemView).navigate(action)
            }

        }catch (e:Exception){
            Log.e(TAGR, "onBindViewHolder: ${e.message}")
        }
    }

}
