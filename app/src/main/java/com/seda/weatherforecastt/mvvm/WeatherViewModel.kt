package com.seda.weatherforecastt.mvvm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seda.weatherforecast.model.WeatherResponse
import com.seda.weatherforecast.model2.havadurumu
import com.seda.weatherforecastt.model2.durumm
import com.seda.weatherforecastt.modelll.ForecastResponse

import com.seda.weatherforecastt.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel
    @Inject
    constructor(private val repository: WeatherRepository):ViewModel() {

    private val disposable = CompositeDisposable()

    val forecastData = MutableLiveData<List<ForecastResponse.Forecast>>()
    var forecastDataList : List<ForecastResponse.Forecast> = ArrayList()

    private val _response = MutableLiveData<WeatherResponse>()
    val responsejob: LiveData<WeatherResponse>
        get() = _response

private val _forecast = MutableLiveData<havadurumu>()
    val forecast:LiveData<havadurumu>
    get() = _forecast

fun weatherresponse(lat : Double, lon :Double, units:String, appid :String){
    viewModelScope.launch {
        repository.getWeather(lat,lon,units,appid).let {
            _response.postValue(it.body())

        }
    }

}
    fun forecastresponse(lat : Double, lon :Double, appid :String){
    disposable.add(repository.getForecast(lat, lon, appid)
        .subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
        .subscribeWith(object :DisposableSingleObserver<ForecastResponse>(){
            override fun onSuccess(t: ForecastResponse) {
                var forecastResponse = t
                forecastDataList = forecastResponse.list!!
                forecastData.value = forecastDataList
                Log.i("BİLGİ : ", "CALIŞTI")

            }

            override fun onError(e: Throwable) {
                Log.i("BİLGİ : ", "HATA " + e.message + " " + e.printStackTrace())

            }

        }))

    }
    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}