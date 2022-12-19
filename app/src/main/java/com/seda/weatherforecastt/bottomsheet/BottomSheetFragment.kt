package com.seda.weatherforecastt.bottomsheet

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.seda.weatherforecastt.R
import com.seda.weatherforecastt.databinding.BottomsheetBinding
import com.seda.weatherforecastt.mvvm.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"
private const val ARG_PARAM5 = "param5"
@AndroidEntryPoint
class BottomSheetFragment : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: String? = null
    private var param4: String? = null
    private var param5: String? = null
    private lateinit var binding: BottomsheetBinding
    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var mealName: String
    private lateinit var mealThumb: String

    override fun getTheme(): Int = R.style.bottomSheetDialogTheme

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
            param3 = it.getString(ARG_PARAM3)
            param4= it.getString(ARG_PARAM4)
            param5 = it.getString(ARG_PARAM5)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = BottomsheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeBottomMeal()
    }

    private fun observeBottomMeal() {
        binding.textView.text = param1
        Glide.with(requireActivity())
            .load("https://openweathermap.org/img/wn/$param2@2x.png")
            .into(binding.imageView3)
        binding.textView2.text = param5
        binding.textView3.text =param3
        binding.textView4.text= param4

       Log.e("param","${param1}")
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String,param2: String,param3:String,param4:String,param5:String) =
            BottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                     putString(ARG_PARAM2,param2)
                    putString(ARG_PARAM3,param3)
                    putString(ARG_PARAM4,param4)
                    putString(ARG_PARAM5,param5)
                }
            }
    }
    }
