package com.example.covid_19trackerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONException

class VietNam : AppCompatActivity() {

    private lateinit var vietnamCases: TextView
    private lateinit var vietnamRecovered: TextView
    private lateinit var vietnamDeaths: TextView
    private lateinit var provinceList: List<Province>
    private lateinit var provinceAdapter: ProvinceRVAdapter
    private lateinit var provinceRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viet_nam)

        vietnamCases = findViewById(R.id.TVVietnamCases)
        vietnamRecovered = findViewById(R.id.TVVietnamRecovered)
        vietnamDeaths = findViewById(R.id.TVVietnamDeaths)

        provinceRV = findViewById(R.id.RVProvinces)

        provinceList = ArrayList()

        getProvincesData()
    }

    private fun getProvincesData() {
        val url =
            "https://api.apify.com/v2/key-value-stores/ZsOpZgeg7dFS1rgfM/records/LATEST?fbclid=IwAR1UCKt-lM0mITqxyalzx-XdQ3cFYX51Il_7kU0X79sS5LDZwdIp7FFPAxg&utm_source=j2team&utm_medium=url_shortener"
        val queue = Volley.newRequestQueue(this)
        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val vnCases = response.getInt("infected")
                val vnRecovered = response.getInt("recovered")
                val vnDeaths = response.getInt("deceased")

                vietnamCases.text = vnCases.toString()
                vietnamRecovered.text = vnRecovered.toString()
                vietnamDeaths.text = vnDeaths.toString()

                val provinceArray = response.getJSONArray("detail")
                for (i in 0 until provinceArray.length()) {
                    val provinceCurrent = provinceArray.getJSONObject(i)
                    val provinceName = provinceCurrent.getString("name")
                    val provinceCases = provinceCurrent.getInt("cases")
                    val provinceCasesToday = provinceCurrent.getInt("casesToday")
                    val provinceDeaths = provinceCurrent.getInt("death")

                    val province =
                        Province(provinceName, provinceCases, provinceCasesToday, provinceDeaths)

                    provinceList = provinceList + province
                }

                provinceAdapter = ProvinceRVAdapter(provinceList)
                provinceRV.layoutManager = LinearLayoutManager(this)
                provinceRV.adapter = provinceAdapter

                provinceAdapter.notifyDataSetChanged()

            } catch (e: JSONException) {
                e.printStackTrace()

            }
        }, {
            Toast.makeText(this, "Fail to get data", Toast.LENGTH_SHORT).show()
        })
        queue.add(request)
    }
}