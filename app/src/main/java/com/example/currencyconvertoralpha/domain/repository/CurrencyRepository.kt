package com.example.currencyconvertoralpha.domain.repository

import com.example.currencyconvertoralpha.data.models.RatesResponse
import com.example.currencyconvertoralpha.util.Resource
import retrofit2.Response

interface CurrencyRepository {
    suspend fun getCurrenciesRates() : Resource<RatesResponse>
}