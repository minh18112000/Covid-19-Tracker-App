package com.example.covid_19trackerapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var worldCases: TextView
    private lateinit var worldRecovered: TextView
    private lateinit var worldDeaths: TextView

    private lateinit var countryList: List<Country>
    private lateinit var countryAdapter: CountryRVAdapter
    private lateinit var countryRV: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        worldCases = findViewById(R.id.TVWorldCases)
        worldRecovered = findViewById(R.id.TVWorldRecovered)
        worldDeaths = findViewById(R.id.TVWorldDeaths)

        countryList = ArrayList()
        countryRV = findViewById(R.id.RVCountries)
        getWorldData()
        getCountriesData()
    }

    private fun getWorldData() {
        val url = "https://disease.sh/v3/covid-19/all"
        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val cases = response.getInt("cases")
                val recovered = response.getInt("recovered")
                val deaths = response.getInt("deaths")

                worldCases.text = cases.toString()
                worldRecovered.text = recovered.toString()
                worldDeaths.text = deaths.toString()
            } catch (e: JSONException) {
                e.printStackTrace()

            }
        }, {
            Toast.makeText(this, "Fail to get data", Toast.LENGTH_SHORT).show()
        })
        queue.add(request)
    }


    private fun getCountriesData() {
        val url = "https://api.covid19api.com/summary"
        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {

                val countryArray = response.getJSONArray("Countries")
                for (i in 0 until countryArray.length()) {
                    val country = countryArray.getJSONObject(i)
                    val countryName = country.getString("Country")
                    val countryCases = country.getInt("TotalConfirmed")
                    val countryRecovered = country.getInt("TotalRecovered")
                    val countryDeaths = country.getInt("TotalDeaths")

                    val countryObject =
                        Country(countryName, countryCases, countryRecovered, countryDeaths)

                    countryList = countryList + countryObject
                }

                countryAdapter = CountryRVAdapter(countryList)
                countryRV.layoutManager = LinearLayoutManager(this)
                countryRV.adapter = countryAdapter

                countryAdapter.notifyDataSetChanged()

            } catch (e: JSONException) {
                e.printStackTrace()

            }
        }, {
            Toast.makeText(this, "Fail to get data", Toast.LENGTH_SHORT).show()
        })
        queue.add(request)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_screen, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item
        when (item.itemId) {
            R.id.vietnam_record -> {
                val intent = Intent(this, VietNam::class.java)
                startActivity(intent)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}