package com.example.neven.conversionapp.di.modules


import com.example.neven.conversionapp.conversion.ConversionActivity
import com.example.neven.conversionapp.di.scopes.ActivityScope
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ConversionActivityBuilder {

      @ActivityScope
      @ContributesAndroidInjector(modules = arrayOf(ConversionModule::class))
      abstract fun bindConversionActivity(): ConversionActivity


}
