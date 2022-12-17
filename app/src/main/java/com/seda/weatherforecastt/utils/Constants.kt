package com.seda.weatherforecastt.utils


import com.seda.weatherforecastt.model2.durumm
import com.seda.weatherforecastt.model3.Day
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

object Constants {
    val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    const val END_POINT: String = "data/2.5/weather"
    const val BASE_URL: String = "http://api.openweathermap.org/"
    const val END_POINT2:String="data/2.5/forecast"

fun days():ArrayList<Day>{
    val dayList = ArrayList<Day>()
val day1 = Day(0,"Monday")
    dayList.add(day1)
    val day2 = Day(1,"Tuesday")
    dayList.add(day2)
    val day3 = Day(2,"Wednesday")
    dayList.add(day3)

    val day4 = Day(3,"Thursday")
    dayList.add(day4)
    val day5 = Day(4,"Friday")
    dayList.add(day5)

    return dayList
}
    fun day():ArrayList<durumm>{
        val dayList = ArrayList<durumm>()


        return dayList
    }
}