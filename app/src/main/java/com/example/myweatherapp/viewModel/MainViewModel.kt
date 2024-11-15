package com.example.myweatherapp.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myweatherapp.model.WeatherModel
import com.example.myweatherapp.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

private const val TAG = "MainViewModel"
class MainViewModel: ViewModel() {
    private val apiWeatherService = WeatherAPIService();
    private val disposable = CompositeDisposable();

    val weather_data = MutableLiveData<WeatherModel>();
    val weather_error = MutableLiveData<Boolean>();
    val weather_loading = MutableLiveData<Boolean>();

    fun refreshData(cityName:String){
        getDataFromApi(cityName);
    }

    private fun getDataFromApi(cityName: String){
        weather_loading.value = true;
        disposable.add(
            apiWeatherService.getWeatherDataService(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<WeatherModel>(){
                    override fun onSuccess(t: WeatherModel) {
                      weather_data.value = t
                        weather_error.value = false
                        weather_loading.value = false
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                       weather_error.value = true
                        weather_loading.value = false
                         Log.d(TAG, "onError: Error:${e.printStackTrace()}")
                    }
                })
        )
    }
}