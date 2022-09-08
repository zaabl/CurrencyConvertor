package com.example.currencyconvertoralpha.di

import com.example.currencyconvertoralpha.data.remote.CurrencyApi
import com.example.currencyconvertoralpha.data.repository.CurrencyRepositoryImpl
import com.example.currencyconvertoralpha.domain.repository.CurrencyRepository
import com.example.currencyconvertoralpha.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRatesApi(): CurrencyApi {
        return Retrofit.Builder()
                .baseUrl("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(CurrencyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCurrencyRepository(currencyApi: CurrencyApi): CurrencyRepository = CurrencyRepositoryImpl(currencyApi)

    @Singleton
    @Provides
    fun provideDispatchers(): DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined
    }

}