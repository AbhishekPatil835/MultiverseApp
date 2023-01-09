package com.bookmyshow.multiverseofmovies.android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bookmyshow.multiverseofmovies.R
import com.bookmyshow.multiverseofmovies.android.models.Crew
import com.bumptech.glide.Glide


class CrewRecyclerAdapter (private val context: Context) : RecyclerView.Adapter<CrewRecyclerAdapter.CrewRecyclerViewHolder>() {


    private var items = ArrayList<Crew>()


    fun setUpDatedData(items: ArrayList<Crew>) {
        this.items = items
        notifyDataSetChanged()
    }


    class CrewRecyclerViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val imageViewLogo :ImageView = itemView.findViewById(R.id.imageViewLogo)
        val textViewProductionName: TextView = itemView.findViewById(R.id.textViewProductionName)
        val textViewRole: TextView = itemView.findViewById(R.id.textViewRole)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CrewRecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.production_recycler_item, parent, false)
        return CrewRecyclerViewHolder(view)

    }

    override fun onBindViewHolder(holder: CrewRecyclerViewHolder, position: Int) {

        val url = "https://image.tmdb.org/t/p/original/" + items[position].profilePath

        Glide
            .with(context)
            .load(url)
            .placeholder(R.drawable.langaude_background)
            .into(holder.imageViewLogo)
        holder.textViewRole.text = items[position].job
        holder.textViewProductionName.text = items[position].name
    }

    override fun getItemCount(): Int {
        return items.size
    }
}