package com.seda.weatherforecastt.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seda.weatherforecast.model2.havadurumu
import com.seda.weatherforecastt.R

import com.seda.weatherforecastt.databinding.WeathercardBinding
import com.seda.weatherforecastt.modelll.ForecastResponse
import com.seda.weatherforecastt.utils.Constants
import javax.inject.Inject


class WeatherAdapter @Inject constructor(private val hourlyList: ArrayList<ForecastResponse.Forecast>): RecyclerView.Adapter<WeatherAdapter.WeatherView>() {
    private val colors: Array<String> = arrayOf("#FFD740","#E040FB","#D81B60","#64FFDA","#448AFF")
    private val resim: Array<Int> = arrayOf(R.drawable.dialog,R.drawable.dialog1,R.drawable.dialog2,R.drawable.dialog3,R.drawable.dialog4)

    var onLongClickListener: ((ForecastResponse.Forecast)->Unit)?=null

    inner class WeatherView(val binding: WeathercardBinding): RecyclerView.ViewHolder(binding.root){


    }





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherView {
        return  WeatherView(WeathercardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }


    @SuppressLint("ResourceType", "SetTextI18n")
    override fun onBindViewHolder(holder: WeatherView, position: Int) {
        val todo = hourlyList[position]


          holder.binding.inis.text = todo.main!!.tempMin.toInt().toString().substring(0,2) + " °C"
           holder.binding.dereceWeather.text = todo.main!!.temp.toInt().toString().substring(0,2) + " °C"
        holder.binding.inn.text= todo.main!!.tempMax.toInt().toString().substring(0,2) + " °C"



        holder.binding.cardview.setCardBackgroundColor(Color.parseColor(colors[position%5]))
  val getir = Constants.days().get(position)

        holder.binding.dayname.text = getir.day
       Glide.with(holder.itemView.context)
           .load("https://openweathermap.org/img/wn/" + todo.weather!![0].icon+ "@2x.png")
           .into(holder.binding.imagee)
    Log.e("resimm","${todo.weather!![0].icon}" )


     holder.itemView.setOnClickListener{
         onLongClickListener.let { it->
             if (it != null) {
                 it(hourlyList[position])
             }
         }
     }




    }

    override fun getItemCount(): Int {
        return hourlyList.size-35
    }
    fun updateHourlyList(newHourlyList: List<ForecastResponse.Forecast>){
       hourlyList.clear()
        hourlyList.addAll(newHourlyList)
        notifyDataSetChanged()
    }

}