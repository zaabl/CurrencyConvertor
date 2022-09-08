package com.example.currencyconvertoralpha.viewmodel

import android.util.Log
import androidx.compose.ui.layout.FirstBaseline
import androidx.lifecycle.ViewModel
import com.example.currencyconvertoralpha.data.models.Eur
import com.example.currencyconvertoralpha.domain.repository.CurrencyRepository
import com.example.currencyconvertoralpha.util.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.currencyconvertoralpha.util.Resource
import kotlinx.coroutines.launch
import kotlin.math.round
import kotlin.reflect.full.memberProperties

@HiltViewModel
class MainViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val dispatchers: DispatcherProvider
): ViewModel() {

    sealed class CurrencyEvent {
        class Success(val resultText: String): CurrencyEvent()
        class Failure(val errorText: String): CurrencyEvent()
        object Loading: CurrencyEvent()
        object Empty: CurrencyEvent()
    }

    private val _conversion = MutableStateFlow<CurrencyEvent>(CurrencyEvent.Empty)
    val conversion: StateFlow<CurrencyEvent> = _conversion

    var input1: String = ""
    var input2: String = ""
    lateinit var rates: Eur

    fun loadCurrencies(){
        Log.d("debug", "loadCurrencies: ")
        viewModelScope.launch(dispatchers.io) {
            _conversion.value = CurrencyEvent.Loading
            when(val ratesResponse = currencyRepository.getCurrenciesRates()){
                is Resource.Error -> _conversion.value = CurrencyEvent.Failure(ratesResponse.message!!)
                is Resource.Success -> {
                     rates = ratesResponse.data!!.eur
                    Log.d("debug", "loadCurrencies: ${rates}")
                    _conversion.value = CurrencyEvent.Success("Success")
                    }
                }
            }
    }

    fun setInput(selected : Int, value : String){

        if(input1 == "0.0" || input2 == "0.0"){
            input1 = ""
            input2 = ""
        }
        if(value == "C"){
            input2 = "0.0"
            input1 = "0.0"
        } else if(selected == 1)
                input1 += value
        else {
                input2 += value
        }
    }

    fun convertCurrencies(selected: Int, firstCurrency: String, secondCurrency: String){
        if(this::rates.isInitialized){
            if(selected == 1){
                val rate = getRateForCurrency(firstCurrency)
                val convertedFirstCurrency = round(input1.toDouble() / rate!! * 100) / 100
                val rate2 = getRateForCurrency(secondCurrency)
                val convertedSecondCurrency = round(convertedFirstCurrency * rate2!! * 100) / 100
                input2 =convertedSecondCurrency.toString()
            } else if(selected == 2){
                val rate = getRateForCurrency(secondCurrency)
                val convertedSecondCurrency = round(input2.toDouble() / rate!! * 100) / 100
                val rate2 = getRateForCurrency(firstCurrency)
                val convertedFirstCurrency = round(convertedSecondCurrency * rate2!! * 100) / 100
                input1 =convertedFirstCurrency.toString()
            }
        }
    }

    private fun getRateForCurrency(currency: String) = when (currency) {
        "AED" -> rates.aed
        "CAD" -> rates.cad
        "CNY" -> rates.cny
        "EGP" -> rates.egp
        "EUR" -> rates.eur
        "GBP" -> rates.gbp
        "JPY" -> rates.jpy
        "NZD" -> rates.nzd
        "SAR" -> rates.sar
        "USD" -> rates.usd
        else -> null
    }

    fun getCurrenciesNames(): List<String>{
        val currencies = mutableListOf<String>()

        for(prop in Eur::class.memberProperties){
            currencies.add(prop.name.uppercase())
        }
        return currencies
    }
}