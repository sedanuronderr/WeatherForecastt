package com.seda.weatherforecastt.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.seda.weatherforecastt.databinding.WeathercardBinding
import com.seda.weatherforecastt.model2.durumm
import com.seda.weatherforecastt.model3.Day
import com.seda.weatherforecastt.utils.Constants

class DayAdapter : RecyclerView.Adapter<DayAdapter.WeatherView>() {


    inner class WeatherView(val binding: WeathercardBinding): RecyclerView.ViewHolder(binding.root){

    }

    private val differCallback = object : DiffUtil.ItemCallback<Day>() {

        override fun areItemsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Day, newItem: Day): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
    private val colors: Array<String> = arrayOf("#FFD740","#E040FB","#D81B60","#64FFDA","#448AFF")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherView {
        return  WeatherView(WeathercardBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: WeatherView, position: Int) {
        val todo = differ.currentList[position]
   //   holder.binding.dayname.text = todo.day.toString()
        val getir = Constants.days().get(position)

        holder.binding.dayname.text = getir.day
       holder.binding.cardview.setCardBackgroundColor(Color.parseColor(colors[position%8]))



    }
    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}