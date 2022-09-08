package com.example.currencyconvertoralpha.data.remote

import com.example.currencyconvertoralpha.data.models.RatesResponse
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApi {
    @GET("currencies/eur.json")
    suspend fun getAvailableCurrencies() : Response<RatesResponse>
}