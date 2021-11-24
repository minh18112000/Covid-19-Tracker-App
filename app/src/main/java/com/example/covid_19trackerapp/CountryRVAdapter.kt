package com.example.covid_19trackerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CountryRVAdapter(private val countryList: List<Country>) :
    RecyclerView.Adapter<CountryRVAdapter.CountryViewHolder>() {

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val countryName: TextView = itemView.findViewById(R.id.TVCountryName)
        val countryCases: TextView = itemView.findViewById(R.id.TVCountryCases)
        val countryRecovered: TextView = itemView.findViewById(R.id.TVCountryRecovered)
        val countryDeaths: TextView = itemView.findViewById(R.id.TVCountryDeaths)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.country_item,
            parent,
            false
        )
        return CountryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countryList[position]
        holder.countryName.text = country.countryName
        holder.countryCases.text = country.countryCases.toString()
        holder.countryRecovered.text = country.countryRecovered.toString()
        holder.countryDeaths.text = country.countryDeaths.toString()
    }

    override fun getItemCount() = countryList.size

}