package com.example.cricketoons.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketoons.R
import com.example.cricketoons.model.apiFixture.Batting
import com.example.cricketoons.viewmodel.ViewModel

private const val TAG = "BattingScorecardAdapter"
class BattingScorecardAdapter(val context: Context, val viewModel: ViewModel,val localTeam: Boolean):
    RecyclerView.Adapter<BattingScorecardAdapter.BattingViewHolder>() {

    private var players = emptyList<Batting>()
    private val scoreboard= if(localTeam)"S1" else "S2"

    class BattingViewHolder(view: View):RecyclerView.ViewHolder(view) {
        val playerName:TextView= view.findViewById(R.id.player_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BattingViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.batting_score_card, parent, false)
        return BattingViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return players.size
    }

    override fun onBindViewHolder(holder: BattingViewHolder, position: Int) {
        val player= players[position]
        holder.playerName.text = player.score.toString()
    }

    fun setDataset(batting: List<Batting>?) {
        if (batting != null) players = batting.filter { it.scoreboard == scoreboard }
        Log.d(TAG, "setDataset: ${players.size}")
    }

}
