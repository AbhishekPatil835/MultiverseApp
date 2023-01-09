package com.bookmyshow.multiverseofmovies.android.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bookmyshow.multiverseofmovies.R
import com.bookmyshow.multiverseofmovies.android.models.SpokenLanguage

class LanguageRecyclerAdapter : RecyclerView.Adapter<LanguageRecyclerAdapter.LanguageRecyclerViewHolder>() {


    private var items = ArrayList<SpokenLanguage>()


    fun setUpDatedData(items: ArrayList<SpokenLanguage>) {
        this.items = items
        notifyDataSetChanged()
    }


    class LanguageRecyclerViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        val textViewLanguage: TextView = itemView.findViewById(R.id.textViewLanguage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageRecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.language_recycler_item, parent, false)
        return LanguageRecyclerViewHolder(view)

    }

    override fun onBindViewHolder(holder: LanguageRecyclerViewHolder, position: Int) {
        holder.textViewLanguage.text = items[position].englishName
    }

    override fun getItemCount(): Int {
        return items.size
    }
}