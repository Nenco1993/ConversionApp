package com.example.neven.conversionapp.conversion

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.SharedPreferences
import com.example.neven.conversionapp.data.ConversionRepository
import com.example.neven.conversionapp.data.ExchangeInfo
import com.example.neven.conversionapp.data.ExchangeRate
import com.example.neven.conversionapp.utils.SchedulersProvider
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


class ConversionViewModel @Inject constructor(
    val repo: ConversionRepository,
    val compositeDisposable: CompositeDisposable,
    val schedulers: SchedulersProvider
) : ViewModel() {

    var liveExchangeRates: MutableLiveData<ConversionResponse> = MutableLiveData()
    var liveResult: MutableLiveData<String> = MutableLiveData()


    init {
        loadData()
    }

    private fun loadData() {
        val flowable: Flowable<List<ExchangeRate>>
        if (isDataOutDated()) {
            flowable = repo.getDataFromAPI()
        } else {
            flowable = repo.getDataFromDB()
        }

        val disposable = flowable
            .subscribeOn(schedulers.io())
            .doOnSubscribe {
                val response = ConversionResponse(ConversionStatus.LOADING, null, null, null)
                liveExchangeRates.value = response
            }
            .doOnNext {
                // TODO save listExchangeRates to database if you have enough time left
            }
            .observeOn(schedulers.main())
            .subscribe(
                {
                    val mutableListCodes = mutableListOf<String>()
                    val listCodes = mutableListCodes
                    it.forEach { rate ->
                        val code = rate.currencyCode
                        if (code != null) {
                            mutableListCodes.add(code)
                        }
                    }
                    val response = ConversionResponse(ConversionStatus.SUCCESS, it, listCodes, null)
                    liveExchangeRates.value = response
                },
                {
                    val response = ConversionResponse(ConversionStatus.ERROR, null, null, it)
                    liveExchangeRates.value = response
                    it.printStackTrace()
                }
            )
        compositeDisposable.add(disposable)
    }


    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    /**
     * for the sake of this assignment I'm gonna set it to outdated every 15 minutes
     */
    private fun isDataOutDated(): Boolean {
        return true
    }

    fun exchange(exchangeInfo: ExchangeInfo) {
        val listRate = exchangeInfo.listExchangeRates
        var valueToKuna = 0.0
        val value = exchangeInfo.value
        for (rate in listRate) {
            if (rate.currencyCode!!.equals(exchangeInfo.fromCurrency, ignoreCase = true)) {
                val fromMedianRate = rate.medianRate?.toDouble()
                valueToKuna = value * fromMedianRate!!
            }
        }
        for (rate in listRate) {
            if (rate.currencyCode!!.equals(exchangeInfo.toCurrency, ignoreCase = true)) {
                val toMedianRate = rate.medianRate?.toDouble()
                val result = valueToKuna / toMedianRate!!
                liveResult.value = result.toString()


            }
        }
    }
}