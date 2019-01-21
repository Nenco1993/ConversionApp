package com.example.neven.conversionapp.conversion

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import com.example.neven.conversionapp.R
import com.example.neven.conversionapp.base.BaseActivity
import com.example.neven.conversionapp.data.ExchangeRate
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.conversion_activity.*
import org.jetbrains.anko.toast
import javax.inject.Inject
import android.widget.ArrayAdapter
import com.example.neven.conversionapp.data.ExchangeInfo


class ConversionActivity : BaseActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

    @Inject
    lateinit var viewmodelFactory: ConversionViewmodelFactory

    lateinit var viewmodel: ConversionViewModel

    lateinit var sharedPreferences: SharedPreferences

    var fromCurrency = ""
    var toCurrency = ""
    var listRates = listOf<ExchangeRate>()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.conversion_activity)
        sharedPreferences = this.getPreferences(Context.MODE_PRIVATE)
        viewmodel = ViewModelProviders.of(this, viewmodelFactory).get(ConversionViewModel::class.java)
        viewmodel.liveExchangeRates.observe(this, Observer {
            handleResponse(it)
        })
        bSubmit.setOnClickListener(this)
        viewmodel.liveResult.observe(this, Observer {
            tvResult.text = it
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.bSubmit -> {
                val value = etValue.text.toString()
                if (!value.isEmpty()) {
                    val exchangeInfo = ExchangeInfo(value.toDouble(), fromCurrency, toCurrency, listRates)
                    viewmodel.exchange(exchangeInfo)
                }
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        when (parent?.id) {
            R.id.sFromCurrency -> {
                fromCurrency = parent.getItemAtPosition(position) as String
            }

            R.id.sToCurrency -> {
                toCurrency = parent.getItemAtPosition(position) as String
            }
        }
    }

    fun handleResponse(response: ConversionResponse?) {
        when (response?.status) {
            ConversionStatus.SUCCESS -> {
                response.listExchangeRates?.let {
                    listRates = it
                }
                showData(response)
            }
            ConversionStatus.ERROR -> showErrorMessage(response.error)
            ConversionStatus.LOADING -> showProgressBar()
        }
    }

    private fun showData(response: ConversionResponse) {
        hideProgressBar()
        sFromCurrency.adapter =
                ArrayAdapter(baseContext, android.R.layout.simple_spinner_dropdown_item, response.listCodes)
        sToCurrency.adapter =
                ArrayAdapter(baseContext, android.R.layout.simple_spinner_dropdown_item, response.listCodes)
        sFromCurrency.onItemSelectedListener = this
        sToCurrency.onItemSelectedListener = this
    }

    private fun showErrorMessage(error: Throwable?) {
        hideProgressBar()
        val errorMessage = error?.message ?: getString(R.string.error_message)
        toast(errorMessage)
    }

    fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    fun hideProgressBar() {
        progressBar.visibility = View.INVISIBLE
    }
}
