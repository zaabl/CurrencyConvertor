package com.example.currencyconvertoralpha

import com.example.currencyconvertoralpha.data.remote.CurrencyApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    val api: CurrencyApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }
}