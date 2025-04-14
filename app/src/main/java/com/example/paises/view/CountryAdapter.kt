package com.example.paises.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.paises.R
import com.example.paises.model.Country

class CountryAdapter(
    private val countries: List<Country>,
    private val onItemClick: (Country) -> Unit
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val flagImageView: ImageView = view.findViewById(R.id.flagImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pais_item, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.nameTextView.text = country.name.official
        Glide.with(holder.itemView).load(country.flags.png).into(holder.flagImageView)
        holder.itemView.setOnClickListener { onItemClick(country) }
    }

    override fun getItemCount(): Int = countries.size
}