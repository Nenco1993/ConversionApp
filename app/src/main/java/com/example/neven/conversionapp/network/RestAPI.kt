package com.example.neven.conversionapp.network

import com.example.neven.conversionapp.data.ExchangeRate
import io.reactivex.Flowable
import retrofit2.http.GET

interface RestAPI {

    @GET("v1/rates/daily/")
    fun getExchangeRates(): Flowable<List<ExchangeRate>>
}