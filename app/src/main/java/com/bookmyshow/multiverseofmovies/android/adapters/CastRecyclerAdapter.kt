package com.bookmyshow.multiverseofmovies.android.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bookmyshow.multiverseofmovies.R
import com.bookmyshow.multiverseofmovies.android.models.Cast
import com.bumptech.glide.Glide


class CastRecyclerAdapter (private val context: Context) : RecyclerView.Adapter<CastRecyclerAdapter.CastRecyclerViewHolder>() {


    private var items = ArrayList<Cast>()


    fun setUpDatedData(items: ArrayList<Cast>) {
        this.items = items
        notifyDataSetChanged()
    }


    class CastRecyclerViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val imageViewLogo :ImageView = itemView.findViewById(R.id.imageViewLogo)
        val textViewProductionName: TextView = itemView.findViewById(R.id.textViewProductionName)
        val textViewRole: TextView = itemView.findViewById(R.id.textViewRole)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastRecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.production_recycler_item, parent, false)
        return CastRecyclerViewHolder(view)

    }

    override fun onBindViewHolder(holder: CastRecyclerViewHolder, position: Int) {

        val url = "https://image.tmdb.org/t/p/original/" + items[position].profilePath

        Glide
            .with(context)
            .load(url)
            .placeholder(R.drawable.langaude_background)
            .into(holder.imageViewLogo)
        holder.textViewRole.text = items[position].character
        holder.textViewProductionName.text = items[position].name
    }

    override fun getItemCount(): Int {
        return items.size
    }
}