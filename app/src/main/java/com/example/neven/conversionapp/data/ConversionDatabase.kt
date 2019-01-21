package com.example.neven.conversionapp.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = [ExchangeRate::class], version = 1)
abstract class ConversionDatabase : RoomDatabase() {
    abstract fun conversionDao(): ConversionDao
}