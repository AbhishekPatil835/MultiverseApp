package com.bookmyshow.multiverseofmovies.android.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bookmyshow.multiverseofmovies.R
import com.bookmyshow.multiverseofmovies.android.models.ProductionCompany
import com.bookmyshow.multiverseofmovies.android.models.SpokenLanguage
import com.bookmyshow.multiverseofmovies.network.BASE_URL
import com.bumptech.glide.Glide
import java.io.InputStream
import java.net.URL


class ProductionRecyclerAdapter (private val context: Context) : RecyclerView.Adapter<ProductionRecyclerAdapter.ProductionRecyclerViewHolder>() {


    private var items = ArrayList<ProductionCompany>()


    fun setUpDatedData(items: ArrayList<ProductionCompany>) {
        this.items = items
        notifyDataSetChanged()
    }


    class ProductionRecyclerViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
        val imageViewLogo :ImageView = itemView.findViewById<ImageView>(R.id.imageViewLogo)
        val textViewProductionName: TextView = itemView.findViewById<TextView>(R.id.textViewProductionName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductionRecyclerViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.production_recycler_item, parent, false)
        return ProductionRecyclerAdapter.ProductionRecyclerViewHolder(view)

    }

    override fun onBindViewHolder(holder: ProductionRecyclerViewHolder, position: Int) {

        val url = "https://image.tmdb.org/t/p/original/" + items[position].logoPath

        Glide
            .with(context)
            .load(url)
            .placeholder(R.drawable.langaude_background)
            .into(holder.imageViewLogo)

        holder.textViewProductionName.text = items[position].name
    }

    override fun getItemCount(): Int {
        return items.size
    }
}