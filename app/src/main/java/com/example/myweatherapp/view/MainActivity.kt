package com.example.myweatherapp.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.example.myweatherapp.databinding.ActivityMainBinding
import com.example.myweatherapp.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel:MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProviders.of(this)[MainViewModel::class.java]
        binding.imgSearchCity.setOnClickListener{
            val cityName = binding.edtCityName.text.toString()
            Log.d("MyWeatherApp", "cityName:$cityName")
            viewModel.refreshData(cityName)
            getLiveData()
        }
    }

private fun getLiveData(){
    viewModel.weather_data.observe(this,Observer {data->
        data.let{
            binding.llData.visibility = View.VISIBLE
            binding.tvCityName.text = data.name
            binding.address.text = data.name

            //The Glide library is used to load the weather icon image from the URL
            Glide.with(this)
                .load("https://openweathermap.org/img/wn/" + data.weather.get(0).icon + "@2x.png")
                .into(binding.imgWeatherPictures)

            var tempInt = data.main.temp.toInt();
            binding.tvDegree.text = tempInt.toString() + "°C"

            binding.tvHumidity.text = data.main.humidity.toString() + "%"
            binding.tvWindSpeed.text = data.wind.speed.toString()
            binding.tvLat.text = data.coord.lat.toString()
            binding.tvLon.text = data.coord.lon.toString()
            binding.pbLoading.visibility = View.GONE
        }
    })

    // show error
    viewModel.weather_error.observe(this, Observer { error ->
        error?.let {
            if (error) {
                binding.tvError.visibility = View.VISIBLE
                binding.pbLoading.visibility = View.GONE
                binding.llData.visibility = View.GONE
            } else {
                binding.tvError.visibility = View.GONE
            }
        }
    })
    // show progress bar
    viewModel.weather_loading.observe(this, Observer { loading ->
        loading?.let {
            if (loading) {
                binding.pbLoading.visibility = View.VISIBLE
                binding.tvError.visibility = View.GONE
                binding.llData.visibility = View.GONE
            } else {
                binding.tvError.visibility = View.GONE
            }
        }
    })
}
}