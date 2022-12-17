package com.seda.weatherforecastt

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.seda.weatherforecastt.adapter.DayAdapter
import com.seda.weatherforecastt.adapter.WeatherAdapter
import com.seda.weatherforecastt.databinding.FragmentWeatherBinding
import com.seda.weatherforecastt.mvvm.WeatherViewModel
import com.seda.weatherforecastt.utils.Constants

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherFragment : Fragment() {
    private lateinit var _binding: FragmentWeatherBinding
    private val binding get() = _binding

    private val viewModel: WeatherViewModel by viewModels()
    private  lateinit var todoAdapter : DayAdapter
    private  var weatherAdapter = WeatherAdapter(arrayListOf())

private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWeatherBinding.inflate(layoutInflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationProviderClient =LocationServices.getFusedLocationProviderClient(requireActivity())
getCurrentLocationn()







        viewModel.responsejob.observe(viewLifecycleOwner, Observer {
            response->

            Glide.with(this)
                .load("https://openweathermap.org/img/wn/" + response.weather.get(0).icon + "@2x.png")
                .into(binding.weatherimage)
            binding.derece.text = response.main.temp.toString() + " °C"
            binding.weathername.text = response.weather.get(0).main
            binding.country.text = response.sys.country
            binding.humidity.text= response.main.humidity.toString() + " %"
            binding.wind.text = response.wind.speed.toString() + " km/hr"
        })
RecylerviewSetup()
onclicklistener()
    }

    @SuppressLint("SuspiciousIndentation")
    private fun getCurrentLocationn() {
        if(checkPermissions()){
            if(isLocationEnabledd()){
                if (ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                        requireActivity(),
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                   requestPermissionss()
                    return
                }
                fusedLocationProviderClient.lastLocation.addOnCompleteListener(requireActivity()) {task->
                     val location : Location?= task.result
                    if(location==null){
                        Toast.makeText(requireActivity(),"null recieved",Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(requireActivity(),"Get Success",Toast.LENGTH_SHORT).show()

                        viewModel.weatherresponse(location.latitude,location.longitude,"metric","6b7b8221d88f1514185d51d50283ac45")
                        viewModel.forecastresponse(location.latitude,location.longitude,"6b7b8221d88f1514185d51d50283ac45")
                    }
                  }


            }
            else{
Toast.makeText(requireActivity(),"Turn on location",Toast.LENGTH_SHORT).show()
           val intent = Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)

            }
        }
        else{
            requestPermissionss()

        }
    }
private fun showdialog(){
    var dialog = BottomSheetDialog(requireActivity(),R.style.bottomSheetDialogTheme)

val bottomSheetDialog = LayoutInflater.from(requireActivity()).inflate(R.layout.bottomsheet,null)
val view =bottomSheetDialog.findViewById<View>(R.id.button)
    view.setOnClickListener {
        dialog.dismiss()
    }
    dialog.setContentView(bottomSheetDialog)
    dialog.setCancelable(false)

    dialog.show()
}

    private fun onclicklistener(){
        weatherAdapter.onLongClickListener= {data->
            Toast.makeText(requireActivity(),"${data.main!!.temp}",Toast.LENGTH_SHORT).show()

showdialog()
        }

    }
private fun RecylerviewSetup(){
/*
todoAdapter = DayAdapter()
    binding.rv.apply {
        layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        adapter = todoAdapter

    }

    val list = Constants.days()}
    todoAdapter.differ.submitList(list)*/



    binding.rv.apply {
        layoutManager = LinearLayoutManager(requireActivity(),LinearLayoutManager.HORIZONTAL,false)
        adapter = weatherAdapter


    }
    viewModel.forecastData.observe(viewLifecycleOwner, Observer { forecastgps->
        forecastgps.let {
            weatherAdapter.updateHourlyList(forecastgps)
            Log.e("sonuc","${it.get(0).main!!.temp}")


        }
    })


}



    private fun isLocationEnabledd(): Boolean {
val locationManager:LocationManager = context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        return locationManager.isProviderEnabled(LocationManager.EXTRA_PROVIDER_ENABLED)|| locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    private fun requestPermissionss() {
      ActivityCompat.requestPermissions(requireActivity(),
          arrayOf( android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION),
          PERMISSION_REQUEST_ACCESS_LOCATION)

    }

    private fun checkPermissions(): Boolean {
if(ActivityCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)==PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED)
{
    return true
}
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_ACCESS_LOCATION){
            if(grantResults.isNotEmpty() && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                Toast.makeText(context,"Granted",Toast.LENGTH_SHORT).show()
                getCurrentLocationn()
            }else{
                Toast.makeText(context,"Denied",Toast.LENGTH_SHORT).show()

            }
        }
    }

    companion object{
    private const val PERMISSION_REQUEST_ACCESS_LOCATION =100
}

}