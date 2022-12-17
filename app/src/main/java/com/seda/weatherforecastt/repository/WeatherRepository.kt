package com.seda.weatherforecastt.repository


import com.seda.weatherforecast.model.WeatherResponse

import com.seda.weatherforecastt.api.WeatherService

import retrofit2.Response
import javax.inject.Inject

class WeatherRepository
@Inject
    constructor(private val apiService: WeatherService){
        suspend fun getWeather(lat : Double, lon :Double, units:String, appid :String)
        :Response<WeatherResponse>{
            return apiService.getWeather(lat,lon,units,appid)
        }

 fun getForecast(lat : Double, lon :Double, appid :String)=apiService.getForecast(lat, lon, appid)

}