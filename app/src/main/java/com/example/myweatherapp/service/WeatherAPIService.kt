package com.example.myweatherapp.service

import com.example.myweatherapp.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIService {
    private  val BASE_URL = "https://api.openweathermap.org/"
    private val retrofit = Retrofit.Builder().
            baseUrl(BASE_URL).
            addConverterFactory(GsonConverterFactory.create()).
            addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
            build().
            create(WeatherAPI::class.java)

    fun getWeatherDataService(cityName:String): Single<WeatherModel> {
        return retrofit.getData(cityName);
    }

}