package com.seda.weatherforecastt.api

import com.seda.weatherforecast.model.WeatherResponse
import com.seda.weatherforecast.model2.havadurumu
import com.seda.weatherforecastt.modelll.ForecastResponse
import com.seda.weatherforecastt.utils.Constants
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET(Constants.END_POINT)
  suspend  fun getWeather(
@Query("lat") lat : Double,
@Query("lon") lon :Double,
@Query("units") units:String,
@Query("appid") appid :String
    ): Response<WeatherResponse>

  @GET(Constants.END_POINT2)
 fun getForecast(
      @Query("lat") lat : Double,
      @Query("lon") lon :Double,
      @Query("appid") appid :String
  ): Single<ForecastResponse>
}