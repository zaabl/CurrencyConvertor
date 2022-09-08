package com.example.currencyconvertoralpha.data.repository

import android.util.Log
import com.example.currencyconvertoralpha.data.models.RatesResponse
import com.example.currencyconvertoralpha.data.remote.CurrencyApi
import com.example.currencyconvertoralpha.domain.repository.CurrencyRepository
import com.example.currencyconvertoralpha.util.Resource
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val currencyApi:CurrencyApi
) : CurrencyRepository{
    override suspend fun getCurrenciesRates() : Resource<RatesResponse> {
        Log.d("debug", "getCurrenciesRates: ")
        return try{
            val response = currencyApi.getAvailableCurrencies()
            val result = response.body()
            if(response.isSuccessful && result != null){
                Log.d("debug", result.toString())
                Resource.Success(result)
            }
            else
                Resource.Error(response.message())
        } catch (e: Exception){
            Resource.Error(e.message ?: "An error occurred!")
        }
    }
}