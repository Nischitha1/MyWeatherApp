package com.example.myweatherapp.service

import com.example.myweatherapp.model.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query
import io.reactivex.Single

interface WeatherAPI {

    //http://api.openweathermap.org/data/2.5/weather?q=bingöl&APPID=04a42b96398abc8e4183798ed22f9485

    @GET("data/2.5/weather?&units=metric&APPID=04a42b96398abc8e4183798ed22f9485")
    fun getData(
        @Query("q") cityName: String
    ): Single<WeatherModel>

}