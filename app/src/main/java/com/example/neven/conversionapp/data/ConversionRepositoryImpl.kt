package com.example.neven.conversionapp.data

import com.example.neven.conversionapp.conversion.ConversionResponse
import com.example.neven.conversionapp.network.RestAPI
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class ConversionRepositoryImpl @Inject constructor(val api: RestAPI, val databaseDao: ConversionDao) :
    ConversionRepository {

    override fun getDataFromAPI(): Flowable<List<ExchangeRate>> {
        return api.getExchangeRates()
    }

    override fun getDataFromDB(): Flowable<List<ExchangeRate>> {
        return databaseDao.getAllData()
    }

    override fun saveDataToDB(listExchangeRates: List<ExchangeRate>) {
        return databaseDao.saveData(listExchangeRates)
    }
}