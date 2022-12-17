package com.seda.weatherforecastt.model2
import android.os.Parcelable

import com.seda.weatherforecast.model2.*
//weatherdatamodel
class durumm(
    val clouds: Clouds,
    val dt: Int,
    val dt_txt: String,
    val main: Main,
    val pop: Double,
    val rain: Rain,
    val snow: Snow,
    val sys: Sys,
    val visibility: Int,
    val weather: List<Weather>,
    val wind: Wind
):java.io.Serializable