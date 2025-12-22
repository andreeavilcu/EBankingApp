package com.example.ebankingapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CurrencyResponse (
    @SerializedName("base_code")
    val baseCode: String,

    @SerializedName("time_last_update_utc")
    val lastUpdate: String,

    @SerializedName("rates")
    val rates: Map<String, Double>
)