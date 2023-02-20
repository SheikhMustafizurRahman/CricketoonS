package com.example.cricketoons.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cricketoons.R
import com.example.cricketoons.model.apiFixture.Bowling
import com.example.cricketoons.viewmodel.ViewModel


private const val TAG = "BowlingScorecardAdapter"
class BowlingScorecardAdapter(val context: Context, val viewModel: ViewModel, val localTeam: Boolean):
    RecyclerView.Adapter<BowlingScorecardAdapter.BowlingViewHolder>() {

    private var bowlers = emptyList<Bowling>()
    private val scoreboard= if(localTeam)"S1" else "S2"

    class BowlingViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val playerName: TextView = view.findViewById(R.id.player_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BowlingViewHolder {
        val layout =
            LayoutInflater.from(parent.context).inflate(R.layout.bowling_score_card, parent, false)
        return BowlingViewHolder(layout)
    }

    override fun getItemCount(): Int {
        return bowlers.size
    }

    override fun onBindViewHolder(holder: BowlingViewHolder, position: Int) {
        val bowler= bowlers[position]
        holder.playerName.text = bowler.player_id.toString()
    }

    fun setDataset(bowler: List<Bowling>?) {
        if (bowler != null) bowlers = bowler.filter { it.scoreboard== scoreboard }
        Log.d(TAG, "setDataset: ${bowlers.size}")
    }

}