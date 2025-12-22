package com.example.ebankingapp.data.remote

import com.example.ebankingapp.data.remote.dto.CurrencyResponse
import retrofit2.http.GET

interface CurrencyApi {

    @GET("latest/RON")
    suspend fun getExchangeRates() : CurrencyResponse
}