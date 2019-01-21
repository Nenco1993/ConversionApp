package com.example.neven.conversionapp.di.components

import com.example.neven.conversionapp.ConversionApplication
import com.example.neven.conversionapp.di.modules.AppModule
import com.example.neven.conversionapp.di.modules.ConversionActivityBuilder
import com.example.neven.conversionapp.di.modules.NetModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class, AppModule::class, NetModule::class, ConversionActivityBuilder::class))
interface AppComponent {

    @Component.Builder
    interface AppComponentBuilder {
        @BindsInstance
        fun setApplication(app: ConversionApplication): AppComponentBuilder
        fun build(): AppComponent
    }

    fun inject(activity: ConversionApplication)
}