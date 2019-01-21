package com.example.neven.conversionapp.conversion

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.content.SharedPreferences
import com.example.neven.conversionapp.data.ConversionRepository
import com.example.neven.conversionapp.utils.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import javax.inject.Named


class ConversionViewmodelFactory @Inject constructor(
    val repo: ConversionRepository,
    val compositeDisposable: CompositeDisposable,
    val schedulers: SchedulersProvider
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConversionViewModel::class.java)) {
            return ConversionViewModel(repo, compositeDisposable, schedulers) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}