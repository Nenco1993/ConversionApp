package com.example.neven.conversionapp.data

import io.reactivex.Flowable
import io.reactivex.Single

interface ConversionRepository {

    fun getDataFromAPI(): Flowable<List<ExchangeRate>>
    fun getDataFromDB(): Flowable<List<ExchangeRate>>
    fun saveDataToDB(listExchangeRates: List<ExchangeRate>)
}