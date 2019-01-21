package com.example.neven.conversionapp.conversion

import com.example.neven.conversionapp.data.ExchangeRate

class ConversionResponse(
    val status: ConversionStatus,
    val listExchangeRates: List<ExchangeRate>?,
    val listCodes: List<String>?,
    val error: Throwable?
)




