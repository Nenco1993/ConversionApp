package com.example.neven.conversionapp

import android.app.Activity
import android.app.Application
import com.example.neven.conversionapp.di.components.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class ConversionApplication : Application(), HasActivityInjector {


    @Inject
    lateinit var injector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .setApplication(this)
            .build()
            .inject(this)
        AndroidThreeTen.init(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return injector
    }
}