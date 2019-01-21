package com.example.neven.conversionapp.data

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.neven.conversionapp.conversion.ConversionResponse
import io.reactivex.Flowable
import io.reactivex.Single

@Dao
interface ConversionDao {

    @Query("SELECT * FROM rates")
    fun getAllData(): Flowable<List<ExchangeRate>>

    @Insert
    fun saveData(listExchangeRates: List<ExchangeRate>)
}