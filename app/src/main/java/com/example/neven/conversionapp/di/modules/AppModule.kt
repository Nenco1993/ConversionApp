package com.example.neven.conversionapp.di.modules

import android.arch.persistence.room.Room
import android.content.Context
import android.content.SharedPreferences
import com.example.neven.conversionapp.ConversionApplication
import com.example.neven.conversionapp.conversion.ConversionConstants
import com.example.neven.conversionapp.conversion.ConversionViewmodelFactory
import com.example.neven.conversionapp.data.ConversionDao
import com.example.neven.conversionapp.data.ConversionDatabase
import com.example.neven.conversionapp.data.ConversionRepository
import com.example.neven.conversionapp.data.ConversionRepositoryImpl
import com.example.neven.conversionapp.utils.SchedulersProvider
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.defaultSharedPreferences
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @Provides
    @Singleton
    fun provideConversionRepository(repo: ConversionRepositoryImpl): ConversionRepository {
        return repo
    }

    @Provides
    @Singleton
    fun provideSchedulers(): SchedulersProvider {
        return SchedulersProvider()
    }

    @Provides
    @Singleton
    fun provideConversionViewmodelFactory(
        compositeDisposable: CompositeDisposable,
        repo: ConversionRepositoryImpl,
        schedulers: SchedulersProvider
    ): ConversionViewmodelFactory {
        return ConversionViewmodelFactory(repo, compositeDisposable, schedulers)
    }

    @Provides
    @Singleton
    fun provideContext(application: ConversionApplication): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideRoomDatabase(context: Context): ConversionDatabase {
        return Room.databaseBuilder(
            context,
            ConversionDatabase::class.java,
            ConversionConstants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideReceiptDao(db: ConversionDatabase): ConversionDao {
        return db.conversionDao()
    }

    @Provides
    @Singleton
    fun provideSharedPrefs(context: Context): SharedPreferences {
        return context.defaultSharedPreferences
    }
}