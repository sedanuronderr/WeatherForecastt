package com.seda.weatherforecastt.di


import com.seda.weatherforecastt.api.WeatherService
import com.seda.weatherforecastt.utils.Constants

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = Constants.BASE_URL

    @Singleton
    @Provides //Module içerisinde provides ile tanımlanan fonksiyonları her zaman kullanacaktır.
    fun provideRetrofitInstance(BASE_URL: String): WeatherService =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(Constants.client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherService::class.java)
}